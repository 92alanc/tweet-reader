package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.ui.di.PaidUiModule

class KoinAppDeclarationProviderImpl : KoinAppDeclarationProvider() {

    override fun loadModules() {
        super.loadModules()
        PaidUiModule.load()
    }

}