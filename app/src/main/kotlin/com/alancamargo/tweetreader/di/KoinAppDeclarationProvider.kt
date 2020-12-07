package com.alancamargo.tweetreader.di

import android.app.Application
import com.alancamargo.tweetreader.data.di.DataModule
import com.alancamargo.tweetreader.framework.di.FrameworkModule
import com.alancamargo.tweetreader.ui.di.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

abstract class KoinAppDeclarationProvider {

    fun provideAppDeclaration(application: Application): KoinAppDeclaration = {
        androidContext(application)
        loadModules()
    }

    protected open fun loadModules() {
        DataModule.load()
        FrameworkModule.load()
        UiModule.load()
    }

}