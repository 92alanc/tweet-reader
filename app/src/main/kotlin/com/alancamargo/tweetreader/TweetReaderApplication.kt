package com.alancamargo.tweetreader

import androidx.multidex.MultiDexApplication
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TokenHelperImpl
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.*
import com.alancamargo.tweetreader.viewmodel.TweetViewModelFactory
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class TweetReaderApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        configureImageLoader()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        startKoin {
            androidContext(this@TweetReaderApplication)
            modules(appModule())
        }
        DependencyInjection.init(
            ImageHandlerImpl(), BASE_URL,
            LinkClickListenerImpl()
        )
    }

    private fun configureImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

    private fun appModule() = module {
        factory<ImageHandler> { ImageHandlerImpl() }
        factory<LinkClickListener> { LinkClickListenerImpl() }
        factory<PreferenceHelper> { PreferenceHelperImpl(androidContext()) }
        factory<TokenHelper> { TokenHelperImpl(get()) }
        factory { TweetRepository(get()) }
        factory { TweetViewModelFactory(get()) }
    }

}