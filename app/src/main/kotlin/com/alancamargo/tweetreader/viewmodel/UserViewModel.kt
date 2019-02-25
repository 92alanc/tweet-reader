package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.repository.TwitterCallback
import com.alancamargo.tweetreader.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    fun insert(user: User) {
        repository.insert(user)
    }

    fun getUserDetails(callback: TwitterCallback) {
        repository.select(callback)
    }

}