package com.alancamargo.tweetreader.ui.di

import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelperImpl
import com.alancamargo.tweetreader.ui.ads.AdLoader
import com.alancamargo.tweetreader.ui.ads.AdLoaderImpl
import org.koin.dsl.module

object FreeUiModule : LayerModule() {

    override val module = module {
        factory<AdLoader> { AdLoaderImpl() }
        factory<AdapterHelper> {
            AdapterHelperImpl(
                viewHolderFactory = get(),
                adLoader = get(),
                logger = get()
            )
        }
    }

}