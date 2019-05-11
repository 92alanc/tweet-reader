package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.*
import de.hdodenhof.circleimageview.CircleImageView

class QuotedTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val imgProfilePicture by bindView<CircleImageView>(R.id.img_profile_picture_original)
    private val txtName by bindView<TextView>(R.id.txt_name_original)
    private val txtScreenName by bindView<TextView>(R.id.txt_screen_name_original)
    private val txtTweet by bindView<TextView>(R.id.txt_tweet_original)
    private val txtCreationDate by bindView<TextView>(R.id.txt_creation_date_original)

    override fun bindTo(tweet: Tweet) {
        setImageUrl(imgProfilePicture, tweet.author.profilePictureUrl)
        txtName.text = tweet.author.name
        txtScreenName.text = tweet.author.screenName

        val text = if (tweet.text.hasLink())
            tweet.text.replace(REGEX_URL, "")
        else
            tweet.text

        setTweetText(txtTweet, text.replace("&amp;", "&"))
        setTimestamp(txtCreationDate, tweet.creationDate)
        configureAuthorDataClick(tweet)

        tweet.quotedTweet?.let { quotedTweet ->
            super.bindTo(quotedTweet)
        }
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