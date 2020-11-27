package com.alancamargo.tweetreader.framework.mappers

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.ExtendedTweetResponse

class ExtendedTweetResponseMapper : EntityMapper<ExtendedTweetResponse, ExtendedTweet> {

    override fun map(input: ExtendedTweetResponse) = ExtendedTweet(input.text)

}