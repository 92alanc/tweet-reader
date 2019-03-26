package com.alancamargo.tweetreader.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.BR
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.model.Tweet
import de.hdodenhof.circleimageview.CircleImageView

open class TweetViewHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    open fun bindTo(tweet: Tweet) {
        binding.setVariable(BR.tweet, tweet)
        binding.executePendingBindings()
        configureAuthorDataClick(tweet)
    }

    private fun configureAuthorDataClick(tweet: Tweet) {
        val txtName = binding.root.findViewById<TextView>(R.id.txt_name)
        val txtScreenName = binding.root.findViewById<TextView>(R.id.txt_screen_name)
        val imgProfilePicture = binding.root.findViewById<CircleImageView>(R.id.img_profile_picture)

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