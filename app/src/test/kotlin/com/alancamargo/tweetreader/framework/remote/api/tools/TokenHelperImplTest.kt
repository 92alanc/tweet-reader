package com.alancamargo.tweetreader.framework.remote.api.tools

import com.alancamargo.tweetreader.framework.entities.api.OAuth2Token
import com.alancamargo.tweetreader.data.tools.PreferenceHelper
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class TokenHelperImplTest {

    @MockK lateinit var mockPreferenceHelper: PreferenceHelper

    private val mockWebServer = MockWebServer()

    private lateinit var jsonAdapter: JsonAdapter<OAuth2Token>
    private lateinit var tokenHelper: TokenHelperImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        jsonAdapter = Moshi.Builder().build().adapter(OAuth2Token::class.java)
        val baseUrl = mockWebServer.url("/").toString()
        tokenHelper = TokenHelperImpl(mockPreferenceHelper, baseUrl)
    }

    @Test
    fun ifAccessTokenIsCached_shouldReturnCachedValue() = runBlockingTest {
        every {
            mockPreferenceHelper.getAccessToken()
        } returns "client_credentials token_from_cache"

        assertThat(
            tokenHelper.getAccessToken()
        ).isEqualTo("client_credentials token_from_cache")
    }

    @Test
    fun ifAccessTokenIsNotCached_shouldFetchFromApi() = runBlocking {
        every { mockPreferenceHelper.getAccessToken() } returns null
        enqueueSuccessfulResponse()

        assertThat(
            tokenHelper.getAccessToken()
        ).isEqualTo("client_credentials token_from_api")
    }

    @Test
    fun ifAccessTokenIsFetchedFromApi_shouldUpdateCache() = runBlocking {
        every { mockPreferenceHelper.getAccessToken() } returns null
        enqueueSuccessfulResponse()

        tokenHelper.getAccessToken()

        verify { mockPreferenceHelper.setAccessToken("client_credentials token_from_api") }
    }

    @Test(expected = HttpException::class)
    fun ifApiSendsError_shouldThrowException() {
        runBlocking {
            every { mockPreferenceHelper.getAccessToken() } returns null
            enqueueErrorResponse()

            tokenHelper.getAccessToken()
        }
    }

    private fun enqueueSuccessfulResponse() {
        val token = OAuth2Token("client_credentials", "token_from_api")
        val responseBody = jsonAdapter.toJson(token)
        val tokenResponse = MockResponse().setResponseCode(200).setBody(responseBody)
        mockWebServer.enqueue(tokenResponse)
    }

    private fun enqueueErrorResponse() {
        val response = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(response)
    }

}