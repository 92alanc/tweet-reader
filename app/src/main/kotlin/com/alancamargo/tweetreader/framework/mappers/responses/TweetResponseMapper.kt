package com.alancamargo.tweetreader.framework.mappers.responses

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.ExtendedTweetResponse
import com.alancamargo.tweetreader.framework.entities.MediaResponse
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.framework.entities.UserResponse

class TweetResponseMapper(
        private val userResponseMapper: EntityMapper<UserResponse, User>,
        private val mediaResponseMapper: EntityMapper<MediaResponse, Media>,
        private val extendedTweetResponseMapper: EntityMapper<ExtendedTweetResponse, ExtendedTweet>
) : EntityMapper<TweetResponse, Tweet> {

    override fun map(input: TweetResponse): Tweet = with(input) {
        Tweet(
                id,
                creationDate,
                fullText,
                text,
                userResponseMapper.map(author),
                media?.let(mediaResponseMapper::map),
                quotedTweet?.let(::map),
                retweet?.let(::map),
                inReplyTo,
                extendedTweet?.let(extendedTweetResponseMapper::map),
                repliedTweet?.let(::map)
        )
    }

}