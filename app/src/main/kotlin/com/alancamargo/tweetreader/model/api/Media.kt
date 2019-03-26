package com.alancamargo.tweetreader.model.api

import com.google.gson.annotations.SerializedName

data class Media(@SerializedName("media") val contents: List<MediaContent>?)