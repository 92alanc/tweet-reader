package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.view.View.*
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.ui.activities.BaseProfileActivity
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.setTimestamp
import com.alancamargo.tweetreader.ui.tools.setTweetText
import kotlinx.android.synthetic.main.item_quoted_tweet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class QuotedTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener,
    shareButtonClickListener: ShareButtonClickListener?
) : TweetViewHolder(
    itemView,
    imageHandler,
    linkClickListener,
    shareButtonClickListener
) {

    protected lateinit var originalTweet: TweetResponse

    fun bindQuotedTweet(tweet: TweetResponse) {
        super.bindTo(tweet)
    }

    override fun bindTo(tweet: TweetResponse) {
        originalTweet = tweet
        loadProfilePicture(tweet.author.profilePictureUrl, img_profile_picture_original)
        txt_name_original.text = tweet.author.name
        txt_screen_name_original.text = tweet.author.screenName

        val text = getText(tweet)

        setTweetText(txt_tweet_original, text, linkClickListener)
        setTimestamp(txt_creation_date_original, tweet.creationDate)
        configureAuthorDataClick(tweet)

        progress_bar_share_original.visibility = GONE
        bt_share_original.visibility = VISIBLE
        bt_share_original.isEnabled = true

        bt_share_original.setOnClickListener {
            it.visibility = INVISIBLE
            it.isEnabled = false
            progress_bar_share_original.visibility = VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                shareButtonClickListener?.onShareButtonClicked(tweet)

                withContext(Dispatchers.Main) {
                    progress_bar_share_original.visibility = GONE
                    it.visibility = VISIBLE
                    it.isEnabled = true
                }
            }
        }
    }

    private fun configureAuthorDataClick(tweet: TweetResponse) {
        val clickListener = OnClickListener {
            val context = it.context
            val intent = BaseProfileActivity.getIntent(context, tweet.author)
            context.startActivity(intent)
        }

        txt_name_original.setOnClickListener(clickListener)
        txt_screen_name_original.setOnClickListener(clickListener)
        img_profile_picture_original.setOnClickListener(clickListener)
    }

}