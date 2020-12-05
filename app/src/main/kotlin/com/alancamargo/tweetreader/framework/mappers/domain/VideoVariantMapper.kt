package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.VideoVariantResponse

class VideoVariantMapper : EntityMapper<VideoVariant, VideoVariantResponse> {

    override fun map(input: VideoVariant) = with(input) {
        VideoVariantResponse(contentType, url)
    }

}