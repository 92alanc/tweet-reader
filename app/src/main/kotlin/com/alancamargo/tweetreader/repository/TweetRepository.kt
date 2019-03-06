package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_KEY
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_SECRET
import com.alancamargo.tweetreader.api.CODE_ACCOUNT_SUSPENDED
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.database.TweetDatabase
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.OAuth2Token
import com.alancamargo.tweetreader.util.PreferenceHelper
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository(context: Context) {

    private val api = TwitterApi.getService()
    private val database = TweetDatabase.getInstance(context).tweetDao()
    private val preferenceHelper = PreferenceHelper(context)

    fun insert(tweet: Tweet) {
        database.insert(tweet)
    }

    fun select(callback: TwitterCallback) {
        if (ConnectivityMonitor.isConnected) {
            callApi(callback)
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

    private fun callApi(callback: TwitterCallback) {
        if (preferenceHelper.getAccessToken().isEmpty()) {
            val credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)
            api.postCredentials(credentials).enqueue(object : Callback<OAuth2Token> {
                override fun onResponse(call: Call<OAuth2Token>, response: Response<OAuth2Token>) {
                    response.body()?.let {
                        preferenceHelper.setAccessToken(it.getAuthorisationHeader())
                        getTweetsFromApi(it.getAuthorisationHeader(), callback)
                    }
                }

                override fun onFailure(call: Call<OAuth2Token>, t: Throwable) {
                    Log.e(javaClass.simpleName, t.message, t)
                }
            })
        } else {
            getTweetsFromApi(preferenceHelper.getAccessToken(), callback)
        }
    }

    private fun getTweetsFromApi(authorisationHeader: String, callback: TwitterCallback) {
        api.getTweets(authorisationHeader).enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val tweets = MutableLiveData<List<Tweet>>().apply {
                            value = it
                        }

                        callback.onTweetsFound(tweets)
                    }
                } else if (response.code() == CODE_ACCOUNT_SUSPENDED) {
                    callback.onAccountSuspended()
                }
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    }

}