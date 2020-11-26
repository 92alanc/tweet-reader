package com.alancamargo.tweetreader.framework.di

import com.alancamargo.tweetreader.data.remote.BASE_URL
import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.framework.local.db.TweetDatabaseProvider
import com.alancamargo.tweetreader.framework.remote.api.provider.ApiProvider
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper
import com.alancamargo.tweetreader.framework.tools.connectivity.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object FrameworkModule : LayerModule() {

    override val module = module {
        apiProvider()
        tweetDao()
        connectivityHelper()
        apiHelper()
        deviceManager()
        connectivityStateObserver()
    }

    private fun Module.apiProvider() {
        factory {
            ApiProvider(
                baseUrl = BASE_URL,
                tokenHelper = get()
            )
        }
    }

    private fun Module.tweetDao() {
        factory { TweetDatabaseProvider.getInstance(context = androidContext()).provideDatabase() }
    }

    private fun Module.connectivityHelper() {
        factory<ConnectivityHelper> { ConnectivityHelperImpl(context = androidContext()) }
    }

    private fun Module.apiHelper() {
        factory { ApiHelper(crashReportManager = get()) }
    }

    private fun Module.deviceManager() {
        factory<DeviceManager> { DeviceManagerImpl(connectivityHelper = get()) }
    }

    private fun Module.connectivityStateObserver() {
        factory { ConnectivityStateObserver(deviceManager = get()) }
    }

}