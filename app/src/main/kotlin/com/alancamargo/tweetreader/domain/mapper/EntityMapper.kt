package com.alancamargo.tweetreader.domain.mapper

interface EntityMapper<I, O> {

    fun map(input: I): O

}