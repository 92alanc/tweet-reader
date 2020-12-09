package com.alancamargo.tweetreader.framework.repository

import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.data.tools.Logger
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class TweetRepositoryImplTest {

    @MockK lateinit var mockLocalDataSource: TweetLocalDataSource
    @MockK lateinit var mockRemoteDataSource: TweetRemoteDataSource
    @MockK lateinit var mockLogger: Logger

    private lateinit var repository: TweetRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        val apiHelper = ApiHelper(mockLogger)
        repository = TweetRepositoryImpl(
            mockLocalDataSource,
            mockRemoteDataSource,
            apiHelper
        )
    }

    @Test
    fun shouldGetTweetsFromRemoteDataSource() {
        runBlocking {
            val expected = listOf<Tweet>(mockk(), mockk(), mockk())
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } returns expected

            val result = repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            assertThat(result).isInstanceOf(Result.Success::class.java)
            require(result is Result.Success)
            assertThat(result.body).isEqualTo(expected)
        }
    }

    @Test
    fun whenRemoteDataSourceRespondsWithError_shouldGetTweetsFromCache() {
        runBlocking {
            val expected = listOf<Tweet>(mockk(), mockk(), mockk())
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws Throwable()

            coEvery { mockLocalDataSource.getTweets() } returns expected

            val result = repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            assertThat(result).isInstanceOf(Result.Success::class.java)
            require(result is Result.Success)
            assertThat(result.body).isEqualTo(expected)
        }
    }

    @Test
    fun whenRemoteDataSourceRespondsWithErrorAndCacheThrowsException_shouldReturnGenericError() {
        runBlocking {
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws Throwable()

            coEvery { mockLocalDataSource.getTweets() } throws Throwable()

            val result = repository.getTweets(hasScrolledToBottom = false, isRefreshing = true)

            assertThat(result).isInstanceOf(Result.GenericError::class.java)
        }
    }

    @Test
    fun whenRemoteDataSourceThrowsIOExceptionAndCacheThrowsException_shouldReturnNetworkError() {
        runBlocking {
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws IOException()

            coEvery { mockLocalDataSource.getTweets() } throws Throwable()

            val result = repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            assertThat(result).isInstanceOf(Result.NetworkError::class.java)
        }
    }

    @Test
    fun whenRemoteDataSourceThrowsHttpExceptionAndCacheThrowsException_shouldReturnGenericError() {
        runBlocking {
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws mockHttpException()

            coEvery { mockLocalDataSource.getTweets() } throws Throwable()

            val result = repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            assertThat(result).isInstanceOf(Result.GenericError::class.java)
            require(result is Result.GenericError)
            assertThat(result.code).isEqualTo(404)
        }
    }

    @Test
    fun whenNewTweetsAreFetchedFromRemote_shouldUpdateCache() {
        runBlocking {
            val tweets = listOf<Tweet>(mockk(), mockk(), mockk())
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } returns tweets

            repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            coVerify { mockLocalDataSource.updateCache(tweets) }
        }
    }

    @Test
    fun whenRemoteDataSourceRespondsWithError_shouldLog() {
        runBlocking {
            val t = Throwable()
            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws t

            repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            verify { mockLogger.logException(t) }
        }
    }

    @Test
    fun whenBothRemoteAndLocalDataSourcesRespondWithError_shouldLogErrors() {
        runBlocking {
            val remoteError = Throwable("Remote error!!!")
            val localError = Throwable("Local error!!!")

            coEvery {
                mockRemoteDataSource.getTweets(any(), any())
            } throws remoteError

            coEvery {
                mockLocalDataSource.getTweets()
            } throws localError

            repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            verify { mockLogger.logException(remoteError) }
            verify { mockLogger.logException(localError) }
        }
    }

    @Test
    fun shouldClearCache() = runBlockingTest {
        repository.clearCache()

        coVerify { mockLocalDataSource.clearCache() }
    }

    private fun mockHttpException(): HttpException {
        val body = Response.error<Any>(404, object : ResponseBody() {
            override fun contentLength(): Long = 0L

            override fun contentType(): MediaType = mockk()

            override fun source(): BufferedSource = mockk()
        })

        return HttpException(body)
    }

}