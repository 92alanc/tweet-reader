package com.alancamargo.tweetreader.ui.tools

import android.content.Intent
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.ui.entities.UiTweet

interface SharingHelper {
    suspend fun getShareIntent(tweet: UiTweet): Result<Intent>
}