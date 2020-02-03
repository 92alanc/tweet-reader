package com.alancamargo.tweetreader.util

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

fun <V: View> RecyclerView.ViewHolder.bindView(@IdRes idRes: Int) = lazy {
    itemView.findViewById<V>(idRes)
}
