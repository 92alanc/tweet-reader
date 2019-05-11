package com.alancamargo.tweetreader.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet

class VideoTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val videoView = itemView.findViewById<VideoView>(R.id.video_view)
    private val imgVideo = itemView.findViewById<ImageView>(R.id.img_video)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media?.getVideoUrl()?.let { videoUrl ->
            videoView.run {
                setVideoURI(Uri.parse(videoUrl))
                setMediaController(MediaController(context).also { it.setAnchorView(this) })
                setOnCompletionListener {
                    imgVideo.visibility = View.VISIBLE
                }
            }

            imgVideo.setOnClickListener {
                videoView.start()
                it.visibility = View.GONE
            }
        }
    }

}