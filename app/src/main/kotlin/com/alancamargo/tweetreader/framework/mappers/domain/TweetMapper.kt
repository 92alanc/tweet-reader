package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.ExtendedTweetResponse
import com.alancamargo.tweetreader.framework.entities.MediaResponse
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.framework.entities.UserResponse

class TweetMapper(
    private val userMapper: EntityMapper<User, UserResponse>,
    private val mediaMapper: EntityMapper<Media, MediaResponse>,
    private val extendedTweetMapper: EntityMapper<ExtendedTweet, ExtendedTweetResponse>
) : EntityMapper<Tweet, TweetResponse> {

    override fun map(input: Tweet): TweetResponse = with(input) {
        TweetResponse(
            creationDate,
            id,
            fullText,
            text,
            userMapper.map(author),
            media?.let(mediaMapper::map),
            quotedTweet?.let(::map),
            retweet?.let(::map),
            inReplyTo,
            extendedTweet?.let(extendedTweetMapper::map),
            repliedTweet?.let(::map)
        )
    }

}