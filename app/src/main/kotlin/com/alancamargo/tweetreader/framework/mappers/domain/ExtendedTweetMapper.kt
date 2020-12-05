package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.ExtendedTweetResponse

class ExtendedTweetMapper : EntityMapper<ExtendedTweet, ExtendedTweetResponse> {

    override fun map(input: ExtendedTweet) = ExtendedTweetResponse(input.text)

}