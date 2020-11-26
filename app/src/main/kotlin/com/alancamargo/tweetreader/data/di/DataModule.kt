package com.alancamargo.tweetreader.data.di

import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.BASE_URL
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.data.tools.CrashReportManager
import com.alancamargo.tweetreader.data.tools.PreferenceHelper
import com.alancamargo.tweetreader.data.tools.TokenHelper
import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.framework.local.TweetLocalDataSourceImpl
import com.alancamargo.tweetreader.framework.remote.TweetRemoteDataSourceImpl
import com.alancamargo.tweetreader.framework.remote.api.tools.TokenHelperImpl
import com.alancamargo.tweetreader.framework.repository.TweetRepositoryImpl
import com.alancamargo.tweetreader.framework.tools.CrashReportManagerImpl
import com.alancamargo.tweetreader.framework.tools.PreferenceHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule : LayerModule() {

    override val module = module {
        repository()
        localDataSource()
        remoteDataSource()
        preferenceHelper()
        tokenHelper()
        crashReportManager()
    }

    private fun Module.repository() {
        factory<TweetRepository> {
            TweetRepositoryImpl(
                context = androidContext(),
                localDataSource = get(),
                remoteDataSource = get(),
                apiHelper = get()
            )
        }
    }

    private fun Module.localDataSource() {
        factory<TweetLocalDataSource> {
            TweetLocalDataSourceImpl(
                context = androidContext(),
                tweetDao = get()
            )
        }
    }

    private fun Module.remoteDataSource() {
        factory<TweetRemoteDataSource> {
            TweetRemoteDataSourceImpl(apiProvider = get())
        }
    }

    private fun Module.preferenceHelper() {
        factory<PreferenceHelper> { PreferenceHelperImpl(context = androidContext()) }
    }

    private fun Module.tokenHelper() {
        factory<TokenHelper> {
            TokenHelperImpl(
                preferenceHelper = get(),
                baseUrl = BASE_URL
            )
        }
    }

    private fun Module.crashReportManager() {
        factory<CrashReportManager> { CrashReportManagerImpl() }
    }

}