package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.entities.VideoInfo
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.MediaContentResponse
import com.alancamargo.tweetreader.framework.entities.VideoInfoResponse

class MediaContentMapper(
    private val videoInfoMapper: EntityMapper<VideoInfo, VideoInfoResponse>
) : EntityMapper<MediaContent, MediaContentResponse> {

    override fun map(input: MediaContent) = with(input) {
        MediaContentResponse(type, photoUrl, videoInfo?.let(videoInfoMapper::map))
    }

}