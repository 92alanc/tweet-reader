package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelperImpl
import com.alancamargo.tweetreader.ui.adapter.TweetAdapter
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactory
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactoryImpl
import com.alancamargo.tweetreader.data.remote.BASE_URL
import com.alancamargo.tweetreader.framework.remote.api.provider.ApiProvider
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper
import com.alancamargo.tweetreader.data.tools.TokenHelper
import com.alancamargo.tweetreader.framework.remote.api.tools.TokenHelperImpl
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.framework.local.TweetLocalDataSourceImpl
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.framework.remote.TweetRemoteDataSourceImpl
import com.alancamargo.tweetreader.framework.local.db.TweetDatabaseProvider
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.ImageHandlerImpl
import com.alancamargo.tweetreader.data.tools.PreferenceHelper
import com.alancamargo.tweetreader.framework.tools.PreferenceHelperImpl
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.LinkClickListenerImpl
import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.framework.repository.TweetRepositoryImpl
import com.alancamargo.tweetreader.data.tools.CrashReportManager
import com.alancamargo.tweetreader.framework.tools.CrashReportManagerImpl
import com.alancamargo.tweetreader.framework.tools.connectivity.*
import com.alancamargo.tweetreader.ui.viewmodel.TweetViewModel
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
    factory { TweetAdapter(get()) }
    factory<AdapterHelper> {
        AdapterHelperImpl(
            get()
        )
    }
    factory<ViewHolderFactory> { ViewHolderFactoryImpl(get(), get()) }
}

private val device = module {
    factory<DeviceManager> { DeviceManagerImpl(get()) }
    factory { ConnectivityStateObserver(get()) }
}