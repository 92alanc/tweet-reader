package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.api.token.TokenHelper
import com.alancamargo.tweetreader.api.token.TokenHelperImpl
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.*
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModel
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getModules() = listOf(data, api, helpers, ui)

private val data = module {
    viewModel { TweetViewModel(get()) }
    viewModel { PhotoDetailsViewModel(get()) }
    factory { TweetRepository(get()) }
}

private val api = module {
    factory<TokenHelper> { TokenHelperImpl(get(), BASE_URL) }
    factory { ApiProvider(BASE_URL, get()) }
}

private val helpers = module {
    factory<PreferenceHelper> { PreferenceHelperImpl(androidContext()) }
}

private val ui = module {
    factory<ImageHandler> { ImageHandlerImpl() }
    factory<LinkClickListener> { LinkClickListenerImpl() }
    factory { TweetAdapter(get(), get()) }
}