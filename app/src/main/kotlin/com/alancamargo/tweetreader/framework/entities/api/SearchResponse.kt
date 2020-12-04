package com.alancamargo.tweetreader.framework.entities.api

import com.alancamargo.tweetreader.framework.entities.TweetResponse

data class SearchResponse(val results: List<TweetResponse>)