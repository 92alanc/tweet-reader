package com.alancamargo.tweetreader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.repository.TwitterCallback
import com.alancamargo.tweetreader.repository.UserRepository

class UserViewModel(context: Context) : ViewModel() {

    private val repository = UserRepository(context)

    fun insert(user: User) {
        repository.insert(user)
    }

    fun select(callback: TwitterCallback) {
        repository.select(callback)
    }

}