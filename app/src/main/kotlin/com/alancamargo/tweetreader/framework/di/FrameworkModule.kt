package com.alancamargo.tweetreader.framework.di

import com.alancamargo.tweetreader.data.remote.BASE_URL
import com.alancamargo.tweetreader.di.LayerModule
import com.alancamargo.tweetreader.domain.entities.*
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.*
import com.alancamargo.tweetreader.framework.local.db.TweetDatabaseProvider
import com.alancamargo.tweetreader.framework.mappers.domain.*
import com.alancamargo.tweetreader.framework.mappers.responses.*
import com.alancamargo.tweetreader.framework.remote.api.provider.ApiProvider
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper
import com.alancamargo.tweetreader.framework.tools.connectivity.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object FrameworkModule : LayerModule() {

    override val module = module {
        apiProvider()
        tweetDao()
        connectivityHelper()
        apiHelper()
        deviceManager()
        connectivityStateObserver()
        mappers()
    }

    private fun Module.apiProvider() {
        factory {
            ApiProvider(
                baseUrl = BASE_URL,
                tokenHelper = get()
            )
        }
    }

    private fun Module.tweetDao() {
        factory { TweetDatabaseProvider.getInstance(context = androidContext()).provideDatabase() }
    }

    private fun Module.connectivityHelper() {
        factory<ConnectivityHelper> { ConnectivityHelperImpl(context = androidContext()) }
    }

    private fun Module.apiHelper() {
        factory { ApiHelper(crashReportManager = get()) }
    }

    private fun Module.deviceManager() {
        factory<DeviceManager> { DeviceManagerImpl(connectivityHelper = get()) }
    }

    private fun Module.connectivityStateObserver() {
        factory { ConnectivityStateObserver(deviceManager = get()) }
    }

    private fun Module.mappers() {
        responseMappers()
        domainMappers()
    }

    private fun Module.responseMappers() {
        extendedTweetResponseMapper()
        mediaContentResponseMapper()
        mediaResponseMapper()
        tweetResponseMapper()
        userResponseMapper()
        videoInfoResponseMapper()
        videoVariantResponseMapper()
    }

    private fun Module.domainMappers() {
        extendedTweetMapper()
        mediaContentMapper()
        mediaMapper()
        tweetMapper()
        userMapper()
        videoInfoMapper()
        videoVariantMapper()
    }

    // region Mappers
    private fun Module.extendedTweetResponseMapper() {
        factory<EntityMapper<ExtendedTweetResponse, ExtendedTweet>>(
                named(EXTENDED_TWEET_RESPONSE_MAPPER)
        ) {
            ExtendedTweetResponseMapper()
        }
    }

    private fun Module.mediaContentResponseMapper() {
        factory<EntityMapper<MediaContentResponse, MediaContent>>(
                named(MEDIA_CONTENT_RESPONSE_MAPPER)
        ) {
            MediaContentResponseMapper(
                    videoInfoResponseMapper = get(named(VIDEO_INFO_RESPONSE_MAPPER))
            )
        }
    }

    private fun Module.mediaResponseMapper() {
        factory<EntityMapper<MediaResponse, Media>>(named(MEDIA_RESPONSE_MAPPER)) {
            MediaResponseMapper(
                    mediaContentResponseMapper = get(named(MEDIA_CONTENT_RESPONSE_MAPPER))
            )
        }
    }

    private fun Module.tweetResponseMapper() {
        factory<EntityMapper<TweetResponse, Tweet>>(named(TWEET_RESPONSE_MAPPER)) {
            TweetResponseMapper(
                    userResponseMapper = get(named(USER_RESPONSE_MAPPER)),
                    mediaResponseMapper = get(named(MEDIA_RESPONSE_MAPPER)),
                    extendedTweetResponseMapper = get(named(EXTENDED_TWEET_RESPONSE_MAPPER))
            )
        }
    }

    private fun Module.userResponseMapper() {
        factory<EntityMapper<UserResponse, User>>(named(USER_RESPONSE_MAPPER)) {
            UserResponseMapper()
        }
    }

    private fun Module.videoInfoResponseMapper() {
        factory<EntityMapper<VideoInfoResponse, VideoInfo>>(named(VIDEO_INFO_RESPONSE_MAPPER)) {
            VideoInfoResponseMapper(
                    videoVariantResponseMapper = get(named(VIDEO_VARIANT_RESPONSE_MAPPER))
            )
        }
    }

    private fun Module.videoVariantResponseMapper() {
        factory<EntityMapper<VideoVariantResponse, VideoVariant>>(
                named(VIDEO_VARIANT_RESPONSE_MAPPER)
        ) {
            VideoVariantResponseMapper()
        }
    }

    private fun Module.extendedTweetMapper() {
        factory<EntityMapper<ExtendedTweet, ExtendedTweetResponse>>(
            named(EXTENDED_TWEET_MAPPER)
        ) {
            ExtendedTweetMapper()
        }
    }

    private fun Module.mediaContentMapper() {
        factory<EntityMapper<MediaContent, MediaContentResponse>>(
            named(MEDIA_CONTENT_MAPPER)
        ) {
            MediaContentMapper(
                videoInfoMapper = get(named(VIDEO_INFO_MAPPER))
            )
        }
    }

    private fun Module.mediaMapper() {
        factory<EntityMapper<Media, MediaResponse>>(named(MEDIA_MAPPER)) {
            MediaMapper(
                mediaContentMapper = get(named(MEDIA_CONTENT_MAPPER))
            )
        }
    }

    private fun Module.tweetMapper() {
        factory<EntityMapper<Tweet, TweetResponse>>(named(TWEET_MAPPER)) {
            TweetMapper(
                userMapper = get(named(USER_MAPPER)),
                mediaMapper = get(named(MEDIA_MAPPER)),
                extendedTweetMapper = get(named(EXTENDED_TWEET_MAPPER))
            )
        }
    }

    private fun Module.userMapper() {
        factory<EntityMapper<User, UserResponse>>(named(USER_MAPPER)) {
            UserMapper()
        }
    }

    private fun Module.videoInfoMapper() {
        factory<EntityMapper<VideoInfo, VideoInfoResponse>>(named(VIDEO_INFO_MAPPER)) {
            VideoInfoMapper(
                videoVariantMapper = get(named(VIDEO_VARIANT_MAPPER))
            )
        }
    }

    private fun Module.videoVariantMapper() {
        factory<EntityMapper<VideoVariant, VideoVariantResponse>>(
            named(VIDEO_VARIANT_MAPPER)
        ) {
            VideoVariantMapper()
        }
    }
    // endregion

}