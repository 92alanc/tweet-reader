package com.alancamargo.tweetreader.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.MediaController
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import kotlinx.android.synthetic.main.item_tweet_video.*

class VideoTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media?.getVideoUrl()?.let { videoUrl ->
            video_view.run {
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