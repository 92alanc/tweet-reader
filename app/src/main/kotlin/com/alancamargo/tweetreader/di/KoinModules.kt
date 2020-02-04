package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.api.tools.TokenHelper
import com.alancamargo.tweetreader.api.tools.TokenHelperImpl
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.handlers.ImageHandlerImpl
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.helpers.LinkClickListenerImpl
import com.alancamargo.tweetreader.helpers.PreferenceHelper
import com.alancamargo.tweetreader.helpers.PreferenceHelperImpl
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModel
import com.alancamargo.tweetreader.viewmodel.ProfileViewModel
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getModules() = listOf(data, helpers, ui)

private val data = module {
    viewModel { TweetViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { PhotoDetailsViewModel(get()) }
    factory { TweetRepository(get()) }
    factory { ApiProvider(BASE_URL, get()) }
}

private val helpers = module {
    factory<PreferenceHelper> { PreferenceHelperImpl(androidContext()) }
    factory<TokenHelper> { TokenHelperImpl(get(), BASE_URL) }
}

private val ui = module {
    factory<ImageHandler> { ImageHandlerImpl() }
    factory<LinkClickListener> { LinkClickListenerImpl() }
    factory { TweetAdapter(get(), get()) }
}