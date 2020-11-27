package com.alancamargo.tweetreader.framework.mappers

import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.UserResponse

class UserResponseMapper : EntityMapper<UserResponse, User> {

    override fun map(input: UserResponse) = with(input) {
        User(
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