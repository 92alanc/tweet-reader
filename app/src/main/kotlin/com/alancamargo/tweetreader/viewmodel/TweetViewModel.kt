package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetCallback
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.runAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TweetViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope,
    TweetCallback {

    private val job = Job()

    override val coroutineContext = job + Dispatchers.Main

    private val repository = TweetRepository(application)

    private lateinit var view: View
    private lateinit var lifecycleOwner: LifecycleOwner

    private var isRefreshing = false

    fun insert(tweet: Tweet) {
        runAsync {
            if (!repository.contains(tweet))
                repository.insert(tweet)
        }
    }

    fun getTweets(lifecycleOwner: LifecycleOwner,
                  callback: View,
                  maxId: Long? = null,
                  sinceId: Long? = null) {
        this.lifecycleOwner = lifecycleOwner
        this.view = callback
        isRefreshing = sinceId != null

        ConnectivityMonitor.isConnected.observe(lifecycleOwner, Observer { isConnected ->
            runAsync {
                if (isConnected) {
                    repository.fetchFromApi(this, maxId, sinceId)
                } else {
                    repository.fetchFromDatabase(this)
                }
            }
        })
    }

    override fun onAccountSuspended() {
        view.onAccountSuspended()
    }

    override fun onTweetsFound(tweets: LiveData<List<Tweet>?>) {
        launch {
            tweets.observe(lifecycleOwner, Observer {
                it?.let { result ->
                    if (result.isNotEmpty())
                        view.onTweetsFound(result, isRefreshing)
                    else
                        view.onNothingFound()
                }
            })
        }
    }

}