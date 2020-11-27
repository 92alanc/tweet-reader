package com.alancamargo.tweetreader.framework.mappers

import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.MediaContentResponse
import com.alancamargo.tweetreader.framework.entities.VideoInfoResponse

class MediaContentResponseMapper(
        private val videoInfoResponseMapper: EntityMapper<VideoInfoResponse, VideoInfo>
) : EntityMapper<MediaContentResponse, MediaContent> {

    override fun map(input: MediaContentResponse) = with(input) {
        MediaContent(type, photoUrl, videoInfo?.let(videoInfoResponseMapper::map))
    }

}