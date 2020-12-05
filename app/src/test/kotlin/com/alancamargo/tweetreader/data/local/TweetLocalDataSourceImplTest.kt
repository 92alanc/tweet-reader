package com.alancamargo.tweetreader.data.local

import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.framework.local.TweetLocalDataSourceImpl
import com.alancamargo.tweetreader.framework.local.db.TweetDao
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TweetLocalDataSourceImplTest {

    @MockK lateinit var mockDbManager: TweetDao

    private lateinit var localDataSource: TweetLocalDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        localDataSource = TweetLocalDataSourceImpl(
            context = mockk(),
            tweetDao = mockDbManager,
            tweetResponseMapper = mockk(),
            tweetMapper = mockk()
        )
    }

    @Test
    fun shouldGetTweetsFromDatabase() = runBlocking {
        coEvery { mockDbManager.select() } returns listOf(mockk(), mockk())

        assertThat(localDataSource.getTweets().size).isEqualTo(2)
    }

    @Test(expected = Exception::class)
    fun withNoCachedTweets_shouldThrowException() = runBlockingTest {
        coEvery { mockDbManager.select() } returns emptyList()

        localDataSource.getTweets()
    }

    @Test
    fun shouldUpdateCache() = runBlocking {
        val tweets = listOf<Tweet>(mockk(), mockk())
        coEvery { mockDbManager.count(1) } returns 0
        coEvery { mockDbManager.count(2) } returns 0

        localDataSource.updateCache(tweets)

        coVerify(exactly = 2) { mockDbManager.insert(any()) }
    }

    @Test
    fun whenATweetIsAlreadyCached_shouldNotCacheAgain() = runBlocking {
        val tweets = listOf<Tweet>(mockk(), mockk())
        coEvery { mockDbManager.count(1) } returns 1
        coEvery { mockDbManager.count(2) } returns 0

        localDataSource.updateCache(tweets)

        coVerify(exactly = 1) { mockDbManager.insert(any()) }
    }

    @Test
    fun shouldClearCache() = runBlocking {
        localDataSource.clearCache()

        coVerify { mockDbManager.delete() }
    }

}