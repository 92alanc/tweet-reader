package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.device.DeviceManager
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import io.mockk.mockk
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getTestModules() = listOf(data, device)

private val data = module {
    viewModel(override = true) { TweetViewModel(get()) }
    single<TweetRepository>(override = true) { mockk() }
}

private val ui = module {
    single<ImageHandler>(override = true) { mockk() }
}

private val device = module {
    single<DeviceManager>(override = true) { mockk() }
}