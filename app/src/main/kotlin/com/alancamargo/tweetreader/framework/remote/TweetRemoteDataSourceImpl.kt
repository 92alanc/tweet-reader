package com.alancamargo.tweetreader.framework.remote

import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.data.remote.DEFAULT_MAX_SEARCH_RESULTS
import com.alancamargo.tweetreader.framework.remote.api.TwitterApi
import com.alancamargo.tweetreader.framework.remote.api.provider.ApiProvider
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.domain.entities.SearchBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class TweetRemoteDataSourceImpl(private val apiProvider: ApiProvider) : TweetRemoteDataSource {

    override suspend fun getTweets(maxId: Long?, sinceId: Long?): List<TweetResponse> {
        val twitterApi = apiProvider.getTwitterApi()

        return withContext(Dispatchers.IO) {
            twitterApi.getTweets(maxId = maxId, sinceId = sinceId).loadReplies(twitterApi)
        }
    }

    override suspend fun searchTweets(query: String): List<TweetResponse> {
        val searchApi = apiProvider.getSearchApi()
        val twitterApi = apiProvider.getTwitterApi()

        val searchBody = SearchBody.Builder()
            .setQueryTerm(query)
            .setUserId(BuildConfig.USER_ID)
            .setMaxResults(DEFAULT_MAX_SEARCH_RESULTS)
            .build()

        return withContext(Dispatchers.IO) {
            searchApi.search(searchBody).results.loadReplies(twitterApi)
        }
    }

    override suspend fun downloadMedia(mediaUrl: String): InputStream {
        val downloadApi = apiProvider.getDownloadApi()

        return withContext(Dispatchers.IO) {
            downloadApi.download(mediaUrl).byteStream()
        }
    }

    private suspend fun List<TweetResponse>.loadReplies(twitterApi: TwitterApi) = map {
        it.also { tweet ->
            if (tweet.isReply())
                tweet.repliedTweet = loadRepliedTweet(twitterApi, tweet)
        }
    }

    private suspend fun loadRepliedTweet(api: TwitterApi, tweet: TweetResponse): TweetResponse? {
        return tweet.inReplyTo?.let { id ->
            withContext(Dispatchers.IO) {
                api.getTweet(id)
            }
        }
    }

}