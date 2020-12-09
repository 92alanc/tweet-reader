package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.domain.tools.REGEX_URL
import com.alancamargo.tweetreader.domain.tools.hasLink
import com.alancamargo.tweetreader.ui.activities.BaseProfileActivity
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.ads.ImageHandler
import com.alancamargo.tweetreader.ui.ads.setTimestamp
import com.alancamargo.tweetreader.ui.ads.setTweetText
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tweet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class TweetViewHolder(
    itemView: View,
    protected val imageHandler: ImageHandler,
    protected val linkClickListener: LinkClickListener,
    protected val shareButtonClickListener: ShareButtonClickListener?,
    protected val userMapper: EntityMapper<User, UiUser>
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

        progress_bar_share.visibility = GONE
        bt_share.visibility = VISIBLE
        bt_share.isEnabled = true

        bt_share.setOnClickListener {
            it.visibility = INVISIBLE
            it.isEnabled = false
            progress_bar_share.visibility = VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                shareButtonClickListener?.onShareButtonClicked(tweet)

                withContext(Dispatchers.Main) {
                    progress_bar_share.visibility = GONE
                    it.visibility = VISIBLE
                    it.isEnabled = true
                }
            }
        }
    }

    protected fun loadProfilePicture(url: String, imageView: ImageView) {
        CoroutineScope(Dispatchers.Main).launch {
            imageHandler.loadImage(url, imageView)
        }
    }

    protected fun getText(tweet: Tweet): String {
        var text = tweet.extendedTweet?.text ?: tweet.fullText

        if (text.isEmpty())
            text = tweet.text

        if (text.hasLink())
            text = text.replace(REGEX_URL, "")

        return text.replace("&amp;", "&")
    }

    private fun configureAuthorDataClick(tweet: Tweet) {
        val clickListener = OnClickListener {
            val context = it.context
            val author = userMapper.map(tweet.author)
            val intent = BaseProfileActivity.getIntent(context, author)
            context.startActivity(intent)
        }

        txt_name.setOnClickListener(clickListener)
        txt_screen_name.setOnClickListener(clickListener)
        img_profile_picture.setOnClickListener(clickListener)
    }

}