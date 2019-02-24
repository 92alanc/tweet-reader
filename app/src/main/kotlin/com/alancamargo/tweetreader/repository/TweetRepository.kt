package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.BuildConfig.USER_ID
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.database.TweetDatabase
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.ApiTweet
import com.alancamargo.tweetreader.model.database.DatabaseTweet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository(context: Context) {

    private val api = TwitterApi.getService()
    private val database = TweetDatabase.getInstance(context).tweetDao()

    fun insert(tweet: Tweet) {
        database.insert(tweet as DatabaseTweet)
    }

    fun select(callback: TwitterCallback) {
        if (ConnectivityMonitor.isConnected) {
            getTweetsFromApi(callback)
        } else {
            getTweetsFromDatabase(callback)
        }
    }

    private fun getTweetsFromDatabase(callback: TwitterCallback) {
        val tweets = MutableLiveData<List<Tweet>>().apply {
            value = database.select().value
        }

        callback.onTweetsFound(tweets)
    }

    private fun getTweetsFromApi(callback: TwitterCallback) {
        api.getTweets(USER_ID).enqueue(object : Callback<List<ApiTweet>> {
            override fun onResponse(
                call: Call<List<ApiTweet>>,
                response: Response<List<ApiTweet>>
            ) {
                response.body()?.let {
                    val tweets = MutableLiveData<List<Tweet>>().apply {
                        value = it
                    }

                    callback.onTweetsFound(tweets)
                }
            }

            override fun onFailure(call: Call<List<ApiTweet>>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    }

}