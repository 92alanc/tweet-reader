package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.BuildConfig.USER_ID
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.database.UserDatabase
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.model.api.ApiUser
import com.alancamargo.tweetreader.model.database.DatabaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(context: Context) {

    private val api = TwitterApi.getService()
    private val database = UserDatabase.getInstance(context).userDao()

    fun insert(user: User) {
        database.insert(user as DatabaseUser)
    }

    fun select(callback: TwitterCallback) {
        if (ConnectivityMonitor.isConnected) {
            getUserDetailsFromApi(callback)
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

    private fun getUserDetailsFromApi(callback: TwitterCallback) {
        api.getUserDetails(USER_ID).enqueue(object : Callback<ApiUser> {
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