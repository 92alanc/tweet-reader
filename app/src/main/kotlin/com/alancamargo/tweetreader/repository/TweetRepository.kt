package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.api.CODE_ACCOUNT_SUSPENDED
import com.alancamargo.tweetreader.api.CODE_FORBIDDEN
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.database.TwitterDatabase
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.ErrorResponse
import com.alancamargo.tweetreader.util.callApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetRepository(private val context: Context) {

    private val database = TwitterDatabase.getInstance(context).tweetDao()

    fun contains(tweet: Tweet): Boolean = database.count(tweet.id) > 0

    fun insert(tweet: Tweet) {
        Log.d(javaClass.simpleName, "added tweet to database")
        database.insert(tweet)
    }

    fun fetchFromApi(callback: TweetCallback,
                     maxId: Long? = null,
                     sinceId: Long? = null) {
        context.callApi { token ->
            getTweetsFromApi(token, callback, maxId, sinceId)
        }
    }

    fun fetchFromDatabase(callback: TweetCallback) {
        Log.d(javaClass.simpleName, "getTweetsFromDatabase")
        callback.onTweetsFound(database.select())
    }

    @Suppress("UNCHECKED_CAST")
    private fun getTweetsFromApi(authorisationHeader: String,
                                 callback: TweetCallback,
                                 maxId: Long? = null,
                                 sinceId: Long? = null) {
        Log.d(javaClass.simpleName, "getTweetsFromApi")
        val api = TwitterApi.getService()
        api.getTweets(
            authorisationHeader,
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