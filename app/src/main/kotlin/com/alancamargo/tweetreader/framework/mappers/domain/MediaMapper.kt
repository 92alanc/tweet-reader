package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.MediaContentResponse
import com.alancamargo.tweetreader.framework.entities.MediaResponse

class MediaMapper(
    private val mediaContentMapper: EntityMapper<MediaContent, MediaContentResponse>
) : EntityMapper<Media, MediaResponse> {

    override fun map(input: Media) = MediaResponse(input.contents?.map(mediaContentMapper::map))

}