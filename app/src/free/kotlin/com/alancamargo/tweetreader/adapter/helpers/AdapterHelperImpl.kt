package com.alancamargo.tweetreader.adapter.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.viewholder.AdViewHolder
import com.alancamargo.tweetreader.model.Tweet

class AdapterHelperImpl(viewHolderFactory: ViewHolderFactory) : AdapterHelper(viewHolderFactory) {

    override fun getItemViewType(tweet: Tweet, itemPosition: Int): Int {
        val isAd = (itemPosition.toString().endsWith("1"))
                || (itemPosition.toString().endsWith("6"))

        return if (isAd)
            VIEW_TYPE_AD
        else
            super.getItemViewType(tweet, itemPosition)
    }

    override fun getViewHolder(viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_AD)
            inflater.getAdHolder(parent)
        else
            super.getViewHolder(viewType)
    }

    private fun LayoutInflater.getAdHolder(parent: ViewGroup): AdViewHolder {
        val itemView = inflate(R.layout.item_ad, parent, false)
        return AdViewHolder(itemView)
    }

    private companion object {
        const val VIEW_TYPE_AD = 7
    }

}