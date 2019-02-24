package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.google.gson.annotations.SerializedName

data class ApiTweet(
    @SerializedName("created_at") override var creationDate: String,
    @SerializedName("id") override var id: Long,
    @SerializedName("text") override var text: String,
    @SerializedName("user") override var author: User
) : Tweet(creationDate, id, text, author)