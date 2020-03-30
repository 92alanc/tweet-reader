package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.api.tools.ApiHelper
import com.alancamargo.tweetreader.api.tools.TokenHelper
import com.alancamargo.tweetreader.api.tools.TokenHelperImpl
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.local.TweetLocalDataSourceImpl
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSourceImpl
import com.alancamargo.tweetreader.db.TweetDatabaseProvider
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.handlers.ImageHandlerImpl
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.helpers.LinkClickListenerImpl
import com.alancamargo.tweetreader.helpers.PreferenceHelper
import com.alancamargo.tweetreader.helpers.PreferenceHelperImpl
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.repository.TweetRepositoryImpl
import com.alancamargo.tweetreader.util.CrashReportManager
import com.alancamargo.tweetreader.util.CrashReportManagerImpl
import com.alancamargo.tweetreader.util.device.*
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getModules() = listOf(data, helpers, ui, device)

private val data = module {
    viewModel { TweetViewModel(get()) }
    factory<TweetRepository> { TweetRepositoryImpl(androidContext(), get(), get(), get()) }
    factory<TweetLocalDataSource> { TweetLocalDataSourceImpl(androidContext(), get()) }
    factory<TweetRemoteDataSource> { TweetRemoteDataSourceImpl(get()) }
    factory { ApiProvider(BASE_URL, get()) }
    factory { TweetDatabaseProvider.getInstance(androidContext()).provideDatabase() }
}

private val helpers = module {
    factory<PreferenceHelper> { PreferenceHelperImpl(androidContext()) }
    factory<TokenHelper> { TokenHelperImpl(get(), BASE_URL) }
    factory<ConnectivityHelper> { ConnectivityHelperImpl(androidContext()) }
    factory<CrashReportManager> { CrashReportManagerImpl() }
    factory { ApiHelper(get()) }
}

private val ui = module {
    factory<ImageHandler> { ImageHandlerImpl() }
    factory<LinkClickListener> { LinkClickListenerImpl() }
    factory { TweetAdapter(get(), get()) }
}

private val device = module {
    factory<DeviceManager> { DeviceManagerImpl(get()) }
    factory { ConnectivityStateObserver(get()) }
}