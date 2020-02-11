package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.model.Tweet

data class SearchResponse(val results: List<Tweet>)