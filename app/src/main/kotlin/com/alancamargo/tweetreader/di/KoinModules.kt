package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TokenHelperImpl
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.*
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModelFactory
import com.alancamargo.tweetreader.viewmodel.TweetViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun getModules() = listOf(core, api, helpers)

private val core = module {
    factory { TweetRepository(get()) }
    factory { TweetViewModelFactory(get()) }
    factory { PhotoDetailsViewModelFactory(get()) }
}

private val api = module {
    factory<TokenHelper> { TokenHelperImpl(get()) }
}

private val helpers = module {
    factory<PreferenceHelper> { PreferenceHelperImpl(androidContext()) }
    factory<ImageHandler> { ImageHandlerImpl() }
    factory<LinkClickListener> { LinkClickListenerImpl() }
}