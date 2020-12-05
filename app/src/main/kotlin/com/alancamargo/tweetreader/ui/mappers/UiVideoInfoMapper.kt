package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiVideoInfo
import com.alancamargo.tweetreader.ui.entities.UiVideoVariant

class UiVideoInfoMapper(
    private val videoVariantMapper: EntityMapper<VideoVariant, UiVideoVariant>
) : EntityMapper<VideoInfo, UiVideoInfo> {

    override fun map(input: VideoInfo) = with(input) {
        UiVideoInfo(aspectRatio, variants.map(videoVariantMapper::map))
    }

}