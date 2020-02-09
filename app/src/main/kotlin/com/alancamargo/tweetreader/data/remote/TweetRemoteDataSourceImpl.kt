package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.api.DEFAULT_MAX_SEARCH_RESULTS
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.SearchBody

class TweetRemoteDataSourceImpl(private val apiProvider: ApiProvider) : TweetRemoteDataSource {

    override suspend fun getTweets(maxId: Long?, sinceId: Long?): List<Tweet> {
        val twitterApi = apiProvider.getTwitterApi()

        return twitterApi.getTweets(maxId = maxId, sinceId = sinceId).loadReplies(twitterApi)
    }

    override suspend fun searchTweets(query: String): List<Tweet> {
        val searchApi = apiProvider.getSearchApi()
        val twitterApi = apiProvider.getTwitterApi()

        val searchBody = SearchBody.Builder()
            .setQueryTerm(query)
            .setUserId(BuildConfig.USER_ID)
            .setMaxResults(DEFAULT_MAX_SEARCH_RESULTS)
            .build()

        return searchApi.search(searchBody).results.loadReplies(twitterApi)
    }

    private suspend fun List<Tweet>.loadReplies(twitterApi: TwitterApi) = map {
        it.also { tweet ->
            if (tweet.isReply())
                tweet.repliedTweet = loadRepliedTweet(twitterApi, tweet)
        }
    }

    private suspend fun loadRepliedTweet(api: TwitterApi, tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            api.getTweet(id)
        }
    }

}