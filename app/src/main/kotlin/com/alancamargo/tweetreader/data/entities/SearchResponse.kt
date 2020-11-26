package com.alancamargo.tweetreader.data.entities

import com.alancamargo.tweetreader.framework.entities.Tweet

data class SearchResponse(val results: List<Tweet>)