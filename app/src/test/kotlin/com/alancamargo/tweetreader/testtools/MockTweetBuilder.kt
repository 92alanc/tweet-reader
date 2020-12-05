package com.alancamargo.tweetreader.testtools

import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User

class MockTweetBuilder {

    private val tweet = Tweet(
        id = 1L,
        creationDate = "",
        text = "",
        author = User(
            id = 1L,
            name = "",
            screenName = "",
            location = "",
            description = "",
            followersCount = 1,
            creationDate = "",
            profileBannerUrl = "",
            profilePictureUrl = ""
        ),
        fullText = "A random text",
        media = null,
        quotedTweet = null,
        retweet = null,
        inReplyTo = null,
        extendedTweet = null,
        repliedTweet = null
    )

    fun withVideo(): MockTweetBuilder {
        /*tweet.media = Media(
            listOf(
                MediaContent(type = MEDIA_VIDEO, photoUrl = "", videoInfo = null)
            )
        )*/

        return this
    }

    fun withPhoto(): MockTweetBuilder {
        /*tweet.media = Media(
            listOf(
                MediaContent(type = MEDIA_PHOTO, photoUrl = "", videoInfo = null)
            )
        )*/

        return this
    }

    fun withLink(): MockTweetBuilder {
        //tweet.fullText = "https://www.mockurl.co.uk"
        return this
    }

    fun withQuotedTweet(): MockTweetBuilder {
        //tweet.quotedTweet = Tweet()
        return this
    }

    fun withRetweet(): MockTweetBuilder {
        //tweet.retweet = Tweet()
        return this
    }

    fun withReply(): MockTweetBuilder {
        //tweet.inReplyTo = 12345
        return this
    }

    fun build() = tweet

}