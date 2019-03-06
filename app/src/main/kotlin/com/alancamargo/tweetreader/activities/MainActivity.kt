package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.databinding.ActivityMainBinding
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

    private lateinit var binding: ActivityMainBinding

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        recycler_view.adapter = adapter
        userViewModel.getUserDetails(callback = this)
        tweetViewModel.getTweets(callback = this)
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
        userDetails.observe(this, Observer {
            user = it
            binding.user = user
            binding.executePendingBindings()
        })
    }

    override fun onTweetsFound(tweets: LiveData<List<Tweet>>) {
        tweets.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun showProfile() {
        // TODO
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

}
