package com.alancamargo.tweetreader.di

import android.app.Application
import com.alancamargo.tweetreader.data.di.DataModule
import com.alancamargo.tweetreader.framework.di.FrameworkModule
import com.alancamargo.tweetreader.ui.di.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

object KoinAppDeclarationProvider {

    fun provideAppDeclaration(application: Application): KoinAppDeclaration = {
        androidContext(application)
        DataModule.load()
        FrameworkModule.load()
        UiModule.load()
    }

}