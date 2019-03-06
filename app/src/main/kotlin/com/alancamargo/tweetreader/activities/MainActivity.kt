package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.repository.TwitterCallback
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.alancamargo.tweetreader.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TwitterCallback {

    private val adapter = TweetAdapter()

    private val tweetViewModel by lazy {
        ViewModelProviders.of(this).get(TweetViewModel::class.java)
    }

    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.title)
        recycler_view.adapter = adapter
        tweetViewModel.getTweets(callback = this)
        userViewModel.getUserDetails(callback = this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_profile -> {
                showProfile()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onUserDetailsFound(userDetails: LiveData<User>) {
        userDetails.observe(this, Observer { user ->
            this.user = user
        })
    }

    override fun onTweetsFound(tweets: LiveData<List<Tweet>>) {
        tweets.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun showProfile() {
        val intent = ProfileActivity.getIntent(this, user)
        startActivity(intent)
    }

}
