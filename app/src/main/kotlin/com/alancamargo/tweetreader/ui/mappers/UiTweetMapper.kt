package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiExtendedTweet
import com.alancamargo.tweetreader.ui.entities.UiMedia
import com.alancamargo.tweetreader.ui.entities.UiTweet
import com.alancamargo.tweetreader.ui.entities.UiUser

class UiTweetMapper(
    private val userMapper: EntityMapper<User, UiUser>,
    private val mediaMapper: EntityMapper<Media, UiMedia>,
    private val extendedTweetMapper: EntityMapper<ExtendedTweet, UiExtendedTweet>
) : EntityMapper<Tweet, UiTweet> {

    override fun map(input: Tweet): UiTweet = with(input) {
        UiTweet(
            id,
            creationDate,
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