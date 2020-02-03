package com.alancamargo.tweetreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alancamargo.tweetreader.repository.TweetRepository

class TweetViewModelFactory(private val repository: TweetRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TweetViewModel::class.java))
            return TweetViewModel(repository) as T
        else
            throw IllegalStateException("ViewModel not found")
    }

}