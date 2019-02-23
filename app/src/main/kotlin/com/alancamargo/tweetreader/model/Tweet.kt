package com.alancamargo.tweetreader.model

open class Tweet(
    open val creationDate: String,
    open val id: Long,
    open val text: String,
    open val author: User
)