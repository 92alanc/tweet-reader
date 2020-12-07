package com.alancamargo.tweetreader.ui.di

import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelperImpl
import org.koin.dsl.module

object PaidUiModule : LayerModule() {

    override val module = module {
        factory<AdapterHelper> {
            AdapterHelperImpl(viewHolderFactory = get())
        }
    }

}