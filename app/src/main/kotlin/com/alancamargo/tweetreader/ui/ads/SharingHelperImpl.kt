package com.alancamargo.tweetreader.ui.ads

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.data.local.FileType
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharingHelperImpl(
        private val context: Context,
        private val apiHelper: ApiHelper,
        private val remoteDataSource: TweetRemoteDataSource,
        private val localDataSource: TweetLocalDataSource
) : SharingHelper {

    override suspend fun getShareIntent(tweet: Tweet): Result<Intent> {
        return apiHelper.safeApiCall {
            val shareIntent = buildShareIntent(tweet)

            Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.chooser_title_share)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    private suspend fun buildShareIntent(tweet: Tweet): Intent {
        val intent = Intent()

        var text = tweet.extendedTweet?.text ?: tweet.fullText
        if (text.isEmpty())
            text = tweet.text

        text = "*Tweet - ${tweet.author.name}*\n\n$text"
        intent.putExtra(Intent.EXTRA_TEXT, text)

        return when {
            tweet.containsPhoto() -> intent.buildShareImageIntent(tweet)
            tweet.containsVideo() -> intent.buildShareVideoIntent(tweet)
            else -> intent.buildSharePlainTextIntent()
        }
    }

    private suspend fun Intent.buildShareImageIntent(tweet: Tweet) = apply {
        val uris = arrayListOf<Uri>()
        tweet.media!!.getPhotoUrls()!!.forEach {
            val uri = withContext(Dispatchers.IO) {
                val byteStream = remoteDataSource.downloadMedia(it)
                val fileType = FileType.IMAGE
                localDataSource.getFileUri(byteStream, tweet.id.toString(), fileType)
            }
            uris.add(uri)
        }

        action = Intent.ACTION_SEND_MULTIPLE
        type = "image/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
    }

    private suspend fun Intent.buildShareVideoIntent(tweet: Tweet) = apply {
        val uri = withContext(Dispatchers.IO) {
            val byteStream = remoteDataSource.downloadMedia(tweet.media!!.getVideoUrl()!!)
            val fileType = FileType.VIDEO
            localDataSource.getFileUri(byteStream, tweet.id.toString(), fileType)
        }

        action = Intent.ACTION_SEND
        type = "video/*"
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    private fun Intent.buildSharePlainTextIntent() = apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
    }

}