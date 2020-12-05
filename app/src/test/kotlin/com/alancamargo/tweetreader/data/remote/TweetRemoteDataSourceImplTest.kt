package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.data.tools.TokenHelper
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.framework.entities.api.SearchResponse
import com.alancamargo.tweetreader.framework.remote.TweetRemoteDataSourceImpl
import com.alancamargo.tweetreader.framework.remote.api.provider.ApiProvider
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class TweetRemoteDataSourceImplTest {

    @MockK lateinit var mockTokenHelper: TokenHelper

    private val mockWebServer = MockWebServer()

    private lateinit var remoteDataSource: TweetRemoteDataSource
    private lateinit var tweetListJsonAdapter: JsonAdapter<List<*>>
    private lateinit var tweetJsonAdapter: JsonAdapter<TweetResponse>
    private lateinit var searchResponseJsonAdapter: JsonAdapter<SearchResponse>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tweetListJsonAdapter = Moshi.Builder().build().adapter(List::class.java)
        tweetJsonAdapter = Moshi.Builder().build().adapter(TweetResponse::class.java)
        searchResponseJsonAdapter = Moshi.Builder().build().adapter(SearchResponse::class.java)
        val baseUrl = mockWebServer.url("/").toString()
        coEvery { mockTokenHelper.getAccessToken() } returns "mock_token"
        val apiProvider = ApiProvider(baseUrl, mockTokenHelper)
        val mockTweetResponseMapper = mockk<EntityMapper<TweetResponse, Tweet>>()
        remoteDataSource = TweetRemoteDataSourceImpl(apiProvider, mockTweetResponseMapper)
    }

    @Test
    fun shouldFetchTweetsFromApi() = runBlocking {
        enqueueSuccessfulTweetListResponse()

        val tweets = remoteDataSource.getTweets(null, null)

        assertThat(tweets.size).isEqualTo(3)
    }

    @Test
    fun shouldFetchSearchResultsFromApi() = runBlocking {
        enqueueSuccessfulTweetSearchResponse()

        val searchResults = remoteDataSource.searchTweets("Big Smoke")

        assertThat(searchResults.size).isEqualTo(2)
    }

    @Test
    fun whenTweetHasReply_shouldFetchDetails() = runBlocking {
        enqueueSuccessfulTweetListResponse(includeTweetWithReply = true)
        enqueueSuccessfulTweetResponse()

        val tweets = remoteDataSource.getTweets(null, null)

        assertThat(tweets.count { it.repliedTweet != null }).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun ifApiSendsErrorWhenFetchingTweets_shouldThrowException() {
        runBlocking {
            enqueueErrorResponse()

            remoteDataSource.getTweets(null, null)
        }
    }

    @Test(expected = HttpException::class)
    fun ifApiSendsErrorWhenFetchingTweetDetails_shouldThrowException() {
        runBlocking {
            enqueueSuccessfulTweetListResponse(includeTweetWithReply = true)
            enqueueErrorResponse()

            remoteDataSource.getTweets(null, null)
        }
    }

    @Test(expected = HttpException::class)
    fun ifApiSendsErrorWhenSearchingTweets_shouldThrowException() {
        runBlocking {
            enqueueErrorResponse()

            remoteDataSource.searchTweets("CJ")
        }
    }

    private fun enqueueSuccessfulTweetListResponse(includeTweetWithReply: Boolean = false) {
        val tweets = arrayListOf<TweetResponse>(mockk(), mockk(), mockk()).apply {
            if (includeTweetWithReply)
                add(TweetResponse(inReplyTo = 123))
        }
        val body = tweetListJsonAdapter.toJson(tweets)
        val response = MockResponse().setResponseCode(200).setBody(body)
        mockWebServer.enqueue(response)
    }

    private fun enqueueSuccessfulTweetResponse() {
        val tweet = mockk<TweetResponse>()
        val body = tweetJsonAdapter.toJson(tweet)
        val response = MockResponse().setResponseCode(200).setBody(body)
        mockWebServer.enqueue(response)
    }

    private fun enqueueSuccessfulTweetSearchResponse() {
        val searchResults = listOf<TweetResponse>(mockk(), mockk())
        val searchResponse = SearchResponse(searchResults)
        val body = searchResponseJsonAdapter.toJson(searchResponse)
        val response = MockResponse().setResponseCode(200).setBody(body)
        mockWebServer.enqueue(response)
    }

    private fun enqueueErrorResponse() {
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)
    }

}