package com.alancamargo.tweetreader.data.entities

import com.alancamargo.tweetreader.framework.entities.TweetResponse

data class SearchResponse(val results: List<TweetResponse>)