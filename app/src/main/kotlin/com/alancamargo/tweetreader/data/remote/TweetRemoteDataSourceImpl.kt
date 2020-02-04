package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.model.Tweet

class TweetRemoteDataSourceImpl(private val apiProvider: ApiProvider) : TweetRemoteDataSource {

    override suspend fun getTweets(maxId: Long?, sinceId: Long?): List<Tweet> {
        val api = apiProvider.getTwitterApi()

        return api.getTweets(maxId = maxId, sinceId = sinceId).map {
            it.also { tweet ->
                if (tweet.isReply())
                    tweet.repliedTweet = loadRepliedTweet(api, it)
            }
        }
    }

    private suspend fun loadRepliedTweet(api: TwitterApi, tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            api.getTweet(id)
        }
    }

}