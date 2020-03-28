package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.device.DeviceManager
import io.mockk.mockk
import org.koin.dsl.module

fun getTestModules() = listOf(data)

private val data = module {
    factory<TweetRepository>(override = true) { mockk() }
}

private val device = module {
    factory<DeviceManager>(override = true) { mockk() }
}