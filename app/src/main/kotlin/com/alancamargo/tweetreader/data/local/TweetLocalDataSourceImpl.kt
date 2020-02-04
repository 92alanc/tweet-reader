package com.alancamargo.tweetreader.data.local

import com.alancamargo.tweetreader.db.TweetDatabaseManager
import com.alancamargo.tweetreader.model.Tweet

class TweetLocalDataSourceImpl(private val dbManager: TweetDatabaseManager) : TweetLocalDataSource {

    override suspend fun getTweets(): List<Tweet> {
        val tweets = dbManager.select()

        if (tweets.isNotEmpty())
            return tweets
        else
            throw Exception("No tweets found in cache")
    }

    override suspend fun updateCache(tweets: List<Tweet>) {
        tweets.forEach {
            if (!dbManager.hasTweet(it))
                dbManager.insert(it)
        }
    }

    override suspend fun clearCache() {
        dbManager.delete()
    }

    private suspend fun TweetDatabaseManager.hasTweet(tweet: Tweet): Boolean {
        return count(tweet.id) > 0
    }

}