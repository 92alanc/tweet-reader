package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.ui.di.FreeUiModule

class KoinAppDeclarationProviderImpl : KoinAppDeclarationProvider() {

    override fun loadModules() {
        super.loadModules()
        FreeUiModule.load()
    }

}