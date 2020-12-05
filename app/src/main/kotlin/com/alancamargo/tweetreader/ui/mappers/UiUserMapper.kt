package com.alancamargo.tweetreader.ui.mappers

import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiUser

class UiUserMapper : EntityMapper<User, UiUser> {

    override fun map(input: User) = with(input) {
        UiUser(
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