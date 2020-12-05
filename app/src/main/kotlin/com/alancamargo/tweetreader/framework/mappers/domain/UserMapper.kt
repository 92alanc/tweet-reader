package com.alancamargo.tweetreader.framework.mappers.domain

import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.UserResponse

class UserMapper : EntityMapper<User, UserResponse> {

    override fun map(input: User) = with(input) {
        UserResponse(
            id,
            name,
            screenName,
            location,
            description,
            followersCount,
            creationDate,
            profilePictureUrl,
            profileBannerUrl
        )
    }

}