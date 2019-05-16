package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.bindView
import com.alancamargo.tweetreader.util.setImageUrl
import com.alancamargo.tweetreader.util.setTimestamp
import de.hdodenhof.circleimageview.CircleImageView

class RetweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val imgProfilePicture by bindView<CircleImageView>(R.id.img_profile_picture_original)
    private val txtName by bindView<TextView>(R.id.txt_name_original)
    private val txtScreenName by bindView<TextView>(R.id.txt_screen_name_original)
    private val txtCreationDate by bindView<TextView>(R.id.txt_creation_date_original)

    override fun bindTo(tweet: Tweet) {
        setImageUrl(imgProfilePicture, tweet.author.profilePictureUrl)
        txtName.text = tweet.author.name
        txtScreenName.text = tweet.author.screenName
        setTimestamp(txtCreationDate, tweet.creationDate)

        tweet.retweet?.let { retweet ->
            super.bindTo(retweet)
        }
    }

}