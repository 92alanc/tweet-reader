package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.*
import de.hdodenhof.circleimageview.CircleImageView

open class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val context = itemView.context
    private val txtName by bindView<TextView>(R.id.txt_name)
    private val txtScreenName by bindView<TextView>(R.id.txt_screen_name)
    private val imgProfilePicture by bindView<CircleImageView>(R.id.img_profile_picture)
    private val txtTweet by bindView<TextView>(R.id.txt_tweet)
    private val txtCreationDate by bindView<TextView>(R.id.txt_creation_date)

    open fun bindTo(tweet: Tweet) {
        txtName.text = tweet.author.name
        txtScreenName.text = context.getString(R.string.screen_name_format, tweet.author.screenName)
        setImageUrl(imgProfilePicture, tweet.author.profilePictureUrl)
        val text = if (tweet.text.hasLink()) {
            tweet.text.replace(REGEX_URL, "")
        } else {
            tweet.text
        }
        setTweetText(txtTweet, text.replace("&amp;", "&"))
        setTimestamp(txtCreationDate, tweet.creationDate)
        configureAuthorDataClick(tweet)
    }

    private fun configureAuthorDataClick(tweet: Tweet) {
        val clickListener = View.OnClickListener {
            val context = it.context
            val intent = ProfileActivity.getIntent(context, tweet.author)
            context.startActivity(intent)
        }

        txtName.setOnClickListener(clickListener)
        txtScreenName.setOnClickListener(clickListener)
        imgProfilePicture.setOnClickListener(clickListener)
    }

}