package com.alancamargo.tweetreader.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.api.tools.ApiHelper
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.model.Tweet

class TweetRepositoryImpl(
    private val context: Context,
    private val localDataSource: TweetLocalDataSource,
    private val remoteDataSource: TweetRemoteDataSource,
    private val apiHelper: ApiHelper
) : TweetRepository {

    private var tweets: List<Tweet> = emptyList()

    override suspend fun getTweets(
        hasScrolledToBottom: Boolean,
        isRefreshing: Boolean
    ): Result<List<Tweet>> {
        val maxId = if (hasScrolledToBottom) tweets.getMaxId() else null
        val sinceId = if (isRefreshing) tweets.getSinceId() else null

        return apiHelper.safeApiCall(apiCall = {
            val newTweets = remoteDataSource.getTweets(maxId = maxId, sinceId = sinceId).filterNot {
                this.tweets.contains(it)
            }

            localDataSource.updateCache(newTweets)
            tweets = tweets.append(newTweets, isRefreshing)
            tweets
        }, alternative = {
            localDataSource.getTweets()
        })
    }

    override suspend fun searchTweets(query: String): Result<List<Tweet>> = apiHelper.safeApiCall {
        remoteDataSource.searchTweets(query)
    }

    override suspend fun clearCache() {
        localDataSource.clearCache()
    }

    override suspend fun share(tweet: Tweet) {
        val shareIntent = getShareIntent(tweet)

        val chooser = Intent.createChooser(
            shareIntent,
            context.getString(R.string.chooser_title_share)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(chooser)
    }

    private fun List<Tweet>.append(newTweets: List<Tweet>, isRefreshing: Boolean): List<Tweet> {
        return if (isRefreshing)
            newTweets + this
        else
            this + newTweets
    }

    private fun List<Tweet>.getMaxId(): Long? = if (isEmpty()) null else last().id - 1

    private fun List<Tweet>.getSinceId(): Long? = if (isEmpty()) null else first().id

    private fun getShareIntent(tweet: Tweet): Intent = when {
        tweet.containsPhoto() -> getShareImageIntent(tweet)
        tweet.containsVideo() -> getShareVideoIntent(tweet)
        else -> getSharePlainTextIntent(tweet)
    }

    private fun getShareImageIntent(tweet: Tweet) = Intent().apply {
        val uris = arrayListOf<Uri>()
        tweet.media!!.getPhotoUrls()!!.forEach {
            uris.add(Uri.parse(it))
        }

        action = Intent.ACTION_SEND_MULTIPLE
        type = "image/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
    }

    private fun getShareVideoIntent(tweet: Tweet) = Intent().apply {
        val uri = Uri.parse(tweet.media!!.getVideoUrl())

        action = Intent.ACTION_SEND
        type = "video/*"
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    private fun getSharePlainTextIntent(tweet: Tweet) = Intent().apply {
        var text = tweet.extendedTweet?.text ?: tweet.fullText
        if (text.isEmpty())
            text = tweet.text

        text = "*Tweet - ${tweet.author.name}*\n\n$text"

        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

}