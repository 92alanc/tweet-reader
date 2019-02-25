package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_KEY
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_SECRET
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.database.UserDatabase
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.model.api.ApiUser
import com.alancamargo.tweetreader.model.api.OAuth2Token
import com.alancamargo.tweetreader.model.database.DatabaseUser
import com.alancamargo.tweetreader.util.PreferenceHelper
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(context: Context) {

    private val api = TwitterApi.getService()
    private val database = UserDatabase.getInstance(context).userDao()
    private val preferenceHelper = PreferenceHelper(context)

    fun insert(user: User) {
        database.insert(user as DatabaseUser)
    }

    fun select(callback: TwitterCallback) {
        if (ConnectivityMonitor.isConnected) {
            callApi(callback)
        } else {
            getUserDetailsFromDatabase(callback)
        }
    }

    private fun getUserDetailsFromDatabase(callback: TwitterCallback) {
        val userDetails = MutableLiveData<User>().apply {
            value = database.select().value
        }

        callback.onUserDetailsFound(userDetails)
    }

    private fun callApi(callback: TwitterCallback) {
        if (preferenceHelper.getAccessToken().isEmpty()) {
            val credentials = Credentials.basic(CONSUMER_KEY, CONSUMER_SECRET)
            api.postCredentials(credentials).enqueue(object : Callback<OAuth2Token> {
                override fun onResponse(call: Call<OAuth2Token>, response: Response<OAuth2Token>) {
                    response.body()?.let {
                        preferenceHelper.setAccessToken(it.getAuthorisationHeader())
                        getUserDetailsFromApi(it.getAuthorisationHeader(), callback)
                    }
                }

                override fun onFailure(call: Call<OAuth2Token>, t: Throwable) {
                    Log.e(javaClass.simpleName, t.message, t)
                }
            })
        } else {
            getUserDetailsFromApi(preferenceHelper.getAccessToken(), callback)
        }
    }

    private fun getUserDetailsFromApi(authorisationHeader: String, callback: TwitterCallback) {
        api.getUserDetails(authorisationHeader, BuildConfig.USER_ID).enqueue(object : Callback<ApiUser> {
            override fun onResponse(call: Call<ApiUser>, response: Response<ApiUser>) {
                response.body()?.let {
                    val userDetails = MutableLiveData<User>().apply {
                        value = it
                    }

                    callback.onUserDetailsFound(userDetails)
                }
            }

            override fun onFailure(call: Call<ApiUser>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    }

}