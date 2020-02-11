package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.api.tools.ApiHelper
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.CrashReportManager
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class TweetRepositoryTest {

    @MockK lateinit var mockLocalDataSource: TweetLocalDataSource
    @MockK lateinit var mockRemoteDataSource: TweetRemoteDataSource
    @MockK lateinit var mockCrashReportManager: CrashReportManager

    private lateinit var repository: TweetRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        val apiHelper = ApiHelper(mockCrashReportManager)
        repository = TweetRepository(mockLocalDataSource, mockRemoteDataSource, apiHelper)
    }

    @Test
    fun shouldGetTweetsFromRemoteDataSource() {
        runBlocking {
            val expected = listOf<Tweet>(mockk(), mockk(), mockk())
            coEvery {
                mockRemoteDataSource.getTweets(null, null)
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
                mockRemoteDataSource.getTweets(null, null)
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
                mockRemoteDataSource.getTweets(null, null)
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
                mockRemoteDataSource.getTweets(null, null)
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
                mockRemoteDataSource.getTweets(null, null)
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
        val tweets = listOf<Tweet>(mockk(), mockk(), mockk())
        runBlocking {
            coEvery {
                mockRemoteDataSource.getTweets(null, null)
            } returns tweets

            repository.getTweets(hasScrolledToBottom = false, isRefreshing = false)

            coVerify { mockLocalDataSource.updateCache(tweets) }
        }
    }

    private fun mockHttpException(): HttpException {
        val body = Response.error<Any>(404, object : ResponseBody() {
            override fun contentLength(): Long = 0L

            override fun contentType(): MediaType? = mockk()

            override fun source(): BufferedSource = mockk()
        })

        return HttpException(body)
    }

}