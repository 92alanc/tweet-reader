package com.alancamargo.tweetreader.ui.ads

import android.content.Intent
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.domain.entities.Tweet

interface SharingHelper {
    suspend fun getShareIntent(tweet: Tweet): Result<Intent>
}