package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.framework.tools.connectivity.DeviceManager
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.viewmodel.TweetViewModel
import io.mockk.mockk
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getTestModules() = listOf(data, ui, device)

private val data = module {
    viewModel(override = true) {
        TweetViewModel(
            repository = get(),
            sharingHelper = mockk()
        )
    }
    single<TweetRepository>(override = true) { mockk() }
}

private val ui = module {
    single<ImageHandler>(override = true) { mockk(relaxed = true) }
}

private val device = module {
    single<DeviceManager>(override = true) { mockk() }
}