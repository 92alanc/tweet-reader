package com.alancamargo.tweetreader

import android.app.Application
import com.alancamargo.tweetreader.di.KoinAppDeclarationProviderImpl
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import org.koin.core.context.startKoin

@Suppress("registered", "unused")
open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startFirebase()
        startDependencyInjection()
        startImageLoader()
    }

    private fun startFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun startDependencyInjection() {
        val provider = KoinAppDeclarationProviderImpl()
        startKoin(appDeclaration = provider.provideAppDeclaration(this))
    }

    private fun startImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}