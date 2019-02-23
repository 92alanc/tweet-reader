package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.model.Tweet
import com.google.gson.annotations.SerializedName

data class ApiTweet(
    @SerializedName("created_at") override val creationDate: String,
    @SerializedName("id") override val id: Long,
    @SerializedName("text") override val text: String
) : Tweet(creationDate, id, text)