package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.MediaController
import androidx.constraintlayout.widget.ConstraintLayout
import com.alancamargo.tweetreader.ui.entities.UiTweet
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import kotlinx.android.synthetic.main.item_tweet_video.*

class VideoTweetViewHolder(
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

    override fun bindTo(tweet: UiTweet) {
        super.bindTo(tweet)
        tweet.media?.getVideoUrl()?.let { videoUrl ->
            with(video_view) {
                tweet.media?.getVideoAspectRatio()?.let { videoSize ->
                    layoutParams = (layoutParams as ConstraintLayout.LayoutParams).also { params ->
                        params.dimensionRatio = "${videoSize.width}:${videoSize.height}"
                    }
                }

                setVideoURI(Uri.parse(videoUrl))
                setMediaController(MediaController(context).also { it.setAnchorView(this) })
                setOnCompletionListener {
                    img_video.visibility = View.VISIBLE
                }
            }

            img_video.setOnClickListener {
                video_view.start()
                it.visibility = View.GONE
            }
        }
    }

}