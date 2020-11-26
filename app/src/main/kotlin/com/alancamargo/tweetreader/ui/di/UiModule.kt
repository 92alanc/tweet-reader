package com.alancamargo.tweetreader.ui.di

import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.ui.adapter.TweetAdapter
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelperImpl
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactory
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactoryImpl
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.LinkClickListenerImpl
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.ImageHandlerImpl
import com.alancamargo.tweetreader.ui.viewmodel.TweetViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object UiModule : LayerModule() {

    override val module = module {
        viewModel()
        imageHandler()
        linkClickListener()
        tweetAdapter()
        adapterHelper()
        viewHolderFactory()
    }

    private fun Module.viewModel() {
        viewModel { TweetViewModel(repository = get()) }
    }

    private fun Module.imageHandler() {
        factory<ImageHandler> { ImageHandlerImpl() }
    }

    private fun Module.linkClickListener() {
        factory<LinkClickListener> { LinkClickListenerImpl() }
    }

    private fun Module.tweetAdapter() {
        factory { TweetAdapter(adapterHelper = get()) }
    }

    private fun Module.adapterHelper() {
        factory<AdapterHelper> { AdapterHelperImpl(viewHolderFactory = get()) }
    }

    private fun Module.viewHolderFactory() {
        factory<ViewHolderFactory> {
            ViewHolderFactoryImpl(
                imageHandler = get(),
                linkClickListener = get()
            )
        }
    }

}