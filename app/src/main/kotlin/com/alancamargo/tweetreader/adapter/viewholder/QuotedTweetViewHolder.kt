package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.listeners.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.setTimestamp
import com.alancamargo.tweetreader.util.setTweetText
import kotlinx.android.synthetic.main.item_quoted_tweet.*

open class QuotedTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    protected lateinit var originalTweet: Tweet

    fun bindQuotedTweet(tweet: Tweet) {
        super.bindTo(tweet)
    }

    override fun bindTo(tweet: Tweet) {
        originalTweet = tweet
        loadProfilePicture(tweet.author.profilePictureUrl, img_profile_picture_original)
        txt_name_original.text = tweet.author.name
        txt_screen_name_original.text = tweet.author.screenName

        val text = getText(tweet)

        setTweetText(txt_tweet_original, text, linkClickListener)
        setTimestamp(txt_creation_date_original, tweet.creationDate)
        configureAuthorDataClick(tweet)
    }

    private fun configureAuthorDataClick(tweet: Tweet) {
        val clickListener = View.OnClickListener {
            val context = it.context
            val intent = ProfileActivity.getIntent(context, tweet.author)
            context.startActivity(intent)
        }

        txt_name_original.setOnClickListener(clickListener)
        txt_screen_name_original.setOnClickListener(clickListener)
        img_profile_picture_original.setOnClickListener(clickListener)
    }

}