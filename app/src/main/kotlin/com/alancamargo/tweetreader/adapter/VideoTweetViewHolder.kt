package com.alancamargo.tweetreader.adapter

import android.net.Uri
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.api.CONTENT_TYPE_MP4
import com.alancamargo.tweetreader.model.Tweet

class VideoTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val videoView = itemView.findViewById<VideoView>(R.id.video_view)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media
            ?.contents
            ?.first()
            ?.videoInfo
            ?.variants
            ?.first { variant ->
                variant.contentType == CONTENT_TYPE_MP4
            }
            ?.url
            ?.let { videoUrl ->
                videoView.setVideoURI(Uri.parse(videoUrl))
                val mediaController = MediaController(videoView.context)
                videoView.setMediaController(mediaController)
                mediaController.setAnchorView(videoView)
            }
    }

}