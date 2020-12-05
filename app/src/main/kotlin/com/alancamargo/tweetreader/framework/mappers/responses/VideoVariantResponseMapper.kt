package com.alancamargo.tweetreader.framework.mappers.responses

import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.VideoVariantResponse

class VideoVariantResponseMapper : EntityMapper<VideoVariantResponse, VideoVariant> {

    override fun map(input: VideoVariantResponse) = with(input) {
        VideoVariant(contentType, url)
    }

}