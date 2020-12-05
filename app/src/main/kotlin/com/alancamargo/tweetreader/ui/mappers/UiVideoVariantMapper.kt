package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.VideoVariant
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiVideoVariant

class UiVideoVariantMapper : EntityMapper<VideoVariant, UiVideoVariant> {

    override fun map(input: VideoVariant) = with(input) {
        UiVideoVariant(contentType, url)
    }

}