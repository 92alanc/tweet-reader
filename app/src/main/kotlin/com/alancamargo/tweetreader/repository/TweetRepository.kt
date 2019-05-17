package com.alancamargo.tweetreader.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.api.CODE_ACCOUNT_SUSPENDED
import com.alancamargo.tweetreader.api.CODE_FORBIDDEN
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.database.TwitterDatabase
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.ErrorResponse
import com.alancamargo.tweetreader.util.callApi
import com.crashlytics.android.Crashlytics
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository(private val context: Context) {

    private val database = TwitterDatabase.getInstance(context).tweetDao()

    fun contains(tweet: Tweet): Boolean = database.count(tweet.id) > 0

    fun insert(tweet: Tweet) {
        try {
            database.insert(tweet)
        } catch (ex: SQLiteConstraintException) {
            Crashlytics.log("Tweet: ${tweet.toJson()}")
            Crashlytics.logException(ex)
        }
    }

    fun fetchFromApi(callback: TweetCallback,
                     maxId: Long? = null,
                     sinceId: Long? = null) {
        context.callApi { token, api ->
            getTweetsFromApi(token, api, callback, maxId, sinceId)
        }
    }

    fun fetchFromDatabase(callback: TweetCallback) {
        Log.d(javaClass.simpleName, "getTweetsFromDatabase")
        callback.onTweetsFound(database.select())
    }

    fun fetchSingleTweet(id: Long, callback: SingleTweetCallback) {
        context.callApi { token, api ->
            api.getTweet(token, id).enqueue(object : Callback<Tweet> {
                override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                    response.body()?.let { tweet ->
                        callback.onTweetLoaded(tweet)
                    }
                }

                override fun onFailure(call: Call<Tweet>, t: Throwable) {
                    Log.e(javaClass.simpleName, t.message, t)
                    callback.onError()
                }
            })
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getTweetsFromApi(token: String,
                                 api: TwitterApi,
                                 callback: TweetCallback,
                                 maxId: Long? = null,
                                 sinceId: Long? = null) {
        Log.d(javaClass.simpleName, "getTweetsFromApi")
        api.getTweets(
            token,
            maxId = maxId,
            sinceId = sinceId
        ).enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val tweets = MutableLiveData<List<Tweet>>().apply {
                            value = it
                        }

                        callback.onTweetsFound(tweets)
                    }
                } else if (response.code() == CODE_FORBIDDEN) {
                    val errorResponse = response as Response<ErrorResponse>
                    errorResponse.body()?.let {
                        if (it.errors.first().code == CODE_ACCOUNT_SUSPENDED) {
                            callback.onAccountSuspended()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    }

}