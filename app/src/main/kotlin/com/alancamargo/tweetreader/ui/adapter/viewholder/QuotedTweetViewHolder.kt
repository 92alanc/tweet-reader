package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.view.View.*
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.activities.BaseProfileActivity
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.ads.ImageHandler
import com.alancamargo.tweetreader.ui.ads.setTimestamp
import com.alancamargo.tweetreader.ui.ads.setTweetText
import kotlinx.android.synthetic.main.item_quoted_tweet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class QuotedTweetViewHolder(
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

    private fun configureAuthorDataClick(tweet: Tweet) {
        val clickListener = OnClickListener {
            val context = it.context
            val author = userMapper.map(tweet.author)
            val intent = BaseProfileActivity.getIntent(context, author)
            context.startActivity(intent)
        }

        txt_name_original.setOnClickListener(clickListener)
        txt_screen_name_original.setOnClickListener(clickListener)
        img_profile_picture_original.setOnClickListener(clickListener)
    }

}