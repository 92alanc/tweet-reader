package com.alancamargo.tweetreader.framework.mappers

import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.VideoInfoResponse
import com.alancamargo.tweetreader.framework.entities.VideoVariantResponse

class VideoInfoResponseMapper(
        private val videoVariantResponseMapper: EntityMapper<VideoVariantResponse, VideoVariant>
) : EntityMapper<VideoInfoResponse, VideoInfo> {

    override fun map(input: VideoInfoResponse) = with(input) {
        VideoInfo(aspectRatio, variants.map(videoVariantResponseMapper::map))
    }

}