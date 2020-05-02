package com.alancamargo.tweetreader.testtools

import com.alancamargo.tweetreader.api.MEDIA_PHOTO
import com.alancamargo.tweetreader.api.MEDIA_VIDEO
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.Media
import com.alancamargo.tweetreader.model.api.MediaContent

class MockTweetBuilder {

    private val tweet = Tweet(fullText = "A random text")

    fun withVideo(): MockTweetBuilder {
        tweet.media = Media(
            listOf(
                MediaContent(type = MEDIA_VIDEO, photoUrl = "", videoInfo = null)
            )
        )

        return this
    }

    fun withPhoto(): MockTweetBuilder {
        tweet.media = Media(
            listOf(
                MediaContent(type = MEDIA_PHOTO, photoUrl = "", videoInfo = null)
            )
        )

        return this
    }

    fun withLink(): MockTweetBuilder {
        tweet.fullText = "https://www.mockurl.co.uk"
        return this
    }

    fun withQuotedTweet(): MockTweetBuilder {
        tweet.quotedTweet = Tweet()
        return this
    }

    fun withRetweet(): MockTweetBuilder {
        tweet.retweet = Tweet()
        return this
    }

    fun withReply(): MockTweetBuilder {
        tweet.inReplyTo = 12345
        return this
    }

    fun build() = tweet

}