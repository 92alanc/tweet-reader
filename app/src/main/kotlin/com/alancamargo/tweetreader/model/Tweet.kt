package com.alancamargo.tweetreader.model

open class Tweet(
    open var creationDate: String = "",
    open var id: Long = 0,
    open var text: String = "",
    open var author: User = User()
)