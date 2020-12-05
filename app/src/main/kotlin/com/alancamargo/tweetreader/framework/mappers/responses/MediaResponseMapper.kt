package com.alancamargo.tweetreader.framework.mappers.responses

import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.MediaContentResponse
import com.alancamargo.tweetreader.framework.entities.MediaResponse

class MediaResponseMapper(
        private val mediaContentResponseMapper: EntityMapper<MediaContentResponse, MediaContent>
) : EntityMapper<MediaResponse, Media> {

    override fun map(input: MediaResponse) = with(input) {
        Media(contents?.map(mediaContentResponseMapper::map))
    }

}