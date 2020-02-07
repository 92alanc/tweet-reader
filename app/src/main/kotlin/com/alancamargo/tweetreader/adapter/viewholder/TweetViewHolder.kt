package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.REGEX_URL
import com.alancamargo.tweetreader.util.extensions.hasLink
import com.alancamargo.tweetreader.util.setTimestamp
import com.alancamargo.tweetreader.util.setTweetText
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class TweetViewHolder(
    itemView: View,
    protected val imageHandler: ImageHandler,
    protected val linkClickListener: LinkClickListener
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    private val context = itemView.context

    override val containerView: View? = itemView

    open fun bindTo(tweet: Tweet) {
        txt_name.text = tweet.author.name
        txt_screen_name.text = context.getString(R.string.screen_name_format, tweet.author.screenName)
        loadProfilePicture(tweet.author.profilePictureUrl, img_profile_picture)

        val text = getText(tweet)

        setTweetText(txt_tweet, text, linkClickListener)
        setTimestamp(txt_creation_date, tweet.creationDate)
        configureAuthorDataClick(tweet)
    }

    protected fun loadProfilePicture(url: String, imageView: ImageView) {
        CoroutineScope(Dispatchers.Main).launch {
            imageHandler.loadImage(url, imageView)
        }
    }

    protected fun getText(tweet: Tweet): String {
        var text = tweet.fullText

        if (text.isEmpty())
            text = tweet.text

        if (text.hasLink())
            text = text.replace(REGEX_URL, "")

        return text.replace("&amp;", "&")
    }

    private fun configureAuthorDataClick(tweet: Tweet) {
        val clickListener = View.OnClickListener {
            val context = it.context
            val intent = ProfileActivity.getIntent(context, tweet.author)
            context.startActivity(intent)
        }

        txt_name.setOnClickListener(clickListener)
        txt_screen_name.setOnClickListener(clickListener)
        img_profile_picture.setOnClickListener(clickListener)
    }

}