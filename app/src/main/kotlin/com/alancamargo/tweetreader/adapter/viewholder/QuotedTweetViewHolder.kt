package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.*
import de.hdodenhof.circleimageview.CircleImageView

open class QuotedTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    private val imgProfilePicture by bindView<CircleImageView>(R.id.img_profile_picture_original)
    private val txtName by bindView<TextView>(R.id.txt_name_original)
    private val txtScreenName by bindView<TextView>(R.id.txt_screen_name_original)
    private val txtTweet by bindView<TextView>(R.id.txt_tweet_original)
    private val txtCreationDate by bindView<TextView>(R.id.txt_creation_date_original)
    private val icon by bindView<ImageView>(R.id.img_icon)

    lateinit var originalTweet: Tweet

    fun bindQuotedTweet(tweet: Tweet) {
        super.bindTo(tweet)
    }

    override fun bindTo(tweet: Tweet) {
        originalTweet = tweet
        imageHandler.loadImage(tweet.author.profilePictureUrl, imgProfilePicture)
        txtName.text = tweet.author.name
        txtScreenName.text = tweet.author.screenName

        icon.setImageResource(
            if (tweet.isReply()) R.drawable.ic_reply
            else R.drawable.ic_quote

        )

        val text = if (tweet.text.hasLink())
            tweet.text.replace(REGEX_URL, "")
        else
            tweet.text

        setTweetText(txtTweet, text.replace("&amp;", "&"), linkClickListener)
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