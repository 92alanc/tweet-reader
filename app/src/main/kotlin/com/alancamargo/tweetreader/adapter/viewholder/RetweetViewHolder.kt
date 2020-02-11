package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.setTimestamp
import kotlinx.android.synthetic.main.item_retweet.*

class RetweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

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