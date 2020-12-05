package com.alancamargo.tweetreader.ui.di

import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelperImpl
import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.domain.entities.*
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.adapter.TweetAdapter
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactory
import com.alancamargo.tweetreader.ui.adapter.helpers.ViewHolderFactoryImpl
import com.alancamargo.tweetreader.ui.entities.*
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.LinkClickListenerImpl
import com.alancamargo.tweetreader.ui.mappers.*
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.ImageHandlerImpl
import com.alancamargo.tweetreader.ui.tools.SharingHelper
import com.alancamargo.tweetreader.ui.tools.SharingHelperImpl
import com.alancamargo.tweetreader.ui.viewmodel.TweetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object UiModule : LayerModule() {

    override val module = module {
        viewModel()
        imageHandler()
        linkClickListener()
        tweetAdapter()
        adapterHelper()
        viewHolderFactory()
        sharingHelper()
        mappers()
    }

    private fun Module.viewModel() {
        viewModel {
            TweetViewModel(
                repository = get(),
                sharingHelper = get()
            )
        }
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
                linkClickListener = get(),
                userMapper = get(named(UI_USER_MAPPER)),
                mediaMapper = get(named(UI_MEDIA_MAPPER))
            )
        }
    }

    private fun Module.sharingHelper() {
        factory<SharingHelper> {
            SharingHelperImpl(
                context = androidContext(),
                apiHelper = get(),
                remoteDataSource = get(),
                localDataSource = get()
            )
        }
    }

    private fun Module.mappers() {
        extendedTweetMapper()
        mediaContentMapper()
        mediaMapper()
        tweetMapper()
        userMapper()
        videoInfoMapper()
        videoVariantMapper()
    }

    // region Mappers
    private fun Module.extendedTweetMapper() {
        factory<EntityMapper<ExtendedTweet, UiExtendedTweet>>(
            named(UI_EXTENDED_TWEET_MAPPER)
        ) {
            UiExtendedTweetMapper()
        }
    }

    private fun Module.mediaContentMapper() {
        factory<EntityMapper<MediaContent, UiMediaContent>>(
            named(UI_MEDIA_CONTENT_MAPPER)
        ) {
            UiMediaContentMapper(
                videoInfoMapper = get(named(UI_VIDEO_INFO_MAPPER))
            )
        }
    }

    private fun Module.mediaMapper() {
        factory<EntityMapper<Media, UiMedia>>(named(UI_MEDIA_MAPPER)) {
            UiMediaMapper(
                mediaContentMapper = get(named(UI_MEDIA_CONTENT_MAPPER))
            )
        }
    }

    private fun Module.tweetMapper() {
        factory<EntityMapper<Tweet, UiTweet>>(named(UI_TWEET_MAPPER)) {
            UiTweetMapper(
                userMapper = get(named(UI_USER_MAPPER)),
                mediaMapper = get(named(UI_MEDIA_MAPPER)),
                extendedTweetMapper = get(named(UI_EXTENDED_TWEET_MAPPER))
            )
        }
    }

    private fun Module.userMapper() {
        factory<EntityMapper<User, UiUser>>(named(UI_USER_MAPPER)) {
            UiUserMapper()
        }
    }

    private fun Module.videoInfoMapper() {
        factory<EntityMapper<VideoInfo, UiVideoInfo>>(named(UI_VIDEO_INFO_MAPPER)) {
            UiVideoInfoMapper(
                videoVariantMapper = get(named(UI_VIDEO_VARIANT_MAPPER))
            )
        }
    }

    private fun Module.videoVariantMapper() {
        factory<EntityMapper<VideoVariant, UiVideoVariant>>(
            named(UI_VIDEO_VARIANT_MAPPER)
        ) {
            UiVideoVariantMapper()
        }
    }
    // endregion

}