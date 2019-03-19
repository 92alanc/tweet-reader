package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.api.CODE_FORBIDDEN
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.database.UserDatabase
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.callApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val context: Context) {

    private val database = UserDatabase.getInstance(context).userDao()

    fun insert(user: User) {
        database.insert(user)
    }

    fun select(callback: TwitterCallback) {
        if (ConnectivityMonitor.isConnected) {
            context.callApi { token ->
                getUserDetailsFromApi(token, callback)
            }
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

    private fun getUserDetailsFromApi(authorisationHeader: String, callback: TwitterCallback) {
        val api = TwitterApi.getService()
        api.getUserDetails(authorisationHeader).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val userDetails = MutableLiveData<User>().apply {
                            value = it
                        }

                        callback.onUserDetailsFound(userDetails)
                    }
                } else if (response.code() == CODE_FORBIDDEN) {
                    callback.onAccountSuspended()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    }

}