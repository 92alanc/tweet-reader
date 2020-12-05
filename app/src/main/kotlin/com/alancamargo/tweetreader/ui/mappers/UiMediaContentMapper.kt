package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiMediaContent
import com.alancamargo.tweetreader.ui.entities.UiVideoInfo

class UiMediaContentMapper(
    private val videoInfoMapper: EntityMapper<VideoInfo, UiVideoInfo>
) : EntityMapper<MediaContent, UiMediaContent> {

    override fun map(input: MediaContent) = with(input) {
        UiMediaContent(type, photoUrl, videoInfo?.let(videoInfoMapper::map))
    }

}