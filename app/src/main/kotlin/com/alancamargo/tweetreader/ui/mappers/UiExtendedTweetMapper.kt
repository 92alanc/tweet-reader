package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.ExtendedTweet
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiExtendedTweet

class UiExtendedTweetMapper : EntityMapper<ExtendedTweet, UiExtendedTweet> {

    override fun map(input: ExtendedTweet) = UiExtendedTweet(input.text)

}