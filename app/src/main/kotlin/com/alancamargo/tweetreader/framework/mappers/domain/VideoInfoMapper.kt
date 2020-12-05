package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.VideoInfoResponse
import com.alancamargo.tweetreader.framework.entities.VideoVariantResponse

class VideoInfoMapper(
    private val videoVariantMapper: EntityMapper<VideoVariant, VideoVariantResponse>
) : EntityMapper<VideoInfo, VideoInfoResponse> {

    override fun map(input: VideoInfo) = with(input) {
        VideoInfoResponse(aspectRatio, variants.map(videoVariantMapper::map))
    }

}