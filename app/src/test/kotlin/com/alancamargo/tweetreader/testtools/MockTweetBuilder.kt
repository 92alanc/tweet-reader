package com.alancamargo.tweetreader.testtools

import com.alancamargo.tweetreader.data.remote.MEDIA_PHOTO
import com.alancamargo.tweetreader.data.remote.MEDIA_VIDEO
import com.alancamargo.tweetreader.framework.entities.MediaResponse
import com.alancamargo.tweetreader.framework.entities.MediaContentResponse
import com.alancamargo.tweetreader.framework.entities.TweetResponse

class MockTweetBuilder {

    private val tweet = TweetResponse(fullText = "A random text")

    fun withVideo(): MockTweetBuilder {
        tweet.media = MediaResponse(
            listOf(
                MediaContentResponse(type = MEDIA_VIDEO, photoUrl = "", videoInfo = null)
            )
        )

        return this
    }

    fun withPhoto(): MockTweetBuilder {
        tweet.media = MediaResponse(
            listOf(
                MediaContentResponse(type = MEDIA_PHOTO, photoUrl = "", videoInfo = null)
            )
        )

        return this
    }

    fun withLink(): MockTweetBuilder {
        tweet.fullText = "https://www.mockurl.co.uk"
        return this
    }

    fun withQuotedTweet(): MockTweetBuilder {
        tweet.quotedTweet = TweetResponse()
        return this
    }

    fun withRetweet(): MockTweetBuilder {
        tweet.retweet = TweetResponse()
        return this
    }

    fun withReply(): MockTweetBuilder {
        tweet.inReplyTo = 12345
        return this
    }

    fun build() = tweet

}