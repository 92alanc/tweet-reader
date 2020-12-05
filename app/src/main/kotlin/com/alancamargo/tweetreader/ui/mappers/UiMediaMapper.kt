package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.MediaContent
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiMedia
import com.alancamargo.tweetreader.ui.entities.UiMediaContent

class UiMediaMapper(
    private val mediaContentMapper: EntityMapper<MediaContent, UiMediaContent>
) : EntityMapper<Media, UiMedia> {

    override fun map(input: Media) = UiMedia(input.contents?.map(mediaContentMapper::map))

}