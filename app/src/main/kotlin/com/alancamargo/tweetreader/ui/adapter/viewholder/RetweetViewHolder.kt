package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.setTimestamp
import kotlinx.android.synthetic.main.item_retweet.*

class RetweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener,
    shareButtonClickListener: ShareButtonClickListener?,
    userMapper: EntityMapper<User, UiUser>
) : TweetViewHolder(
    itemView,
    imageHandler,
    linkClickListener,
    shareButtonClickListener,
    userMapper
) {

    override fun bindTo(tweet: Tweet) {
        loadProfilePicture(tweet.author.profilePictureUrl, img_profile_picture_original)
        txt_name_original.text = tweet.author.name
        txt_screen_name_original.text = tweet.author.screenName
        setTimestamp(txt_creation_date_original, tweet.creationDate)

        tweet.retweet?.let { retweet ->
            super.bindTo(retweet)
        }
    }

}