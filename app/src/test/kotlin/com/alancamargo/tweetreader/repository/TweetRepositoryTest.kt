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
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
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
    fun whenRemoteDataSourceRespondsWithError_shouldGetTweetsFromLocal() {
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

}