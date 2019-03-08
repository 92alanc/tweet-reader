package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.repository.TwitterCallback
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.alancamargo.tweetreader.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    TwitterCallback,
    Observer<List<Tweet>>,
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter = TweetAdapter()

    private val tweetViewModel by lazy {
        ViewModelProviders.of(this).get(TweetViewModel::class.java)
    }

    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private lateinit var user: User
    private var menu: Menu? = null
    private var tweets: List<Tweet> = listOf()
    private var maxId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.title)
        configureRecyclerView()
        tweetViewModel.getTweets(callback = this)
        userViewModel.getUserDetails(callback = this)
        configureSwipeRefreshLayout()
        progress_bar.visibility = VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
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
        tweets.observe(this, this)
    }

    override fun onAccountSuspended() {
        progress_bar.visibility = GONE

        menu?.findItem(R.id.item_profile)?.let { item ->
            item.isVisible = false
        }

        recycler_view.visibility = GONE
        group_account_suspended.visibility = VISIBLE
    }

    override fun onChanged(tweets: List<Tweet>) {
        if (swipe_refresh_layout.isRefreshing) {
            swipe_refresh_layout.isRefreshing = false
        }

        this.tweets = this.tweets.union(tweets).toList()
        maxId = this.tweets.last().id
        progress_bar.visibility = GONE
        adapter.submitList(tweets)
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        tweetViewModel.getTweets(callback = this)
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        /*recycler_view.addOnScrollListener(object :
            EndlessScrollListener(recycler_view.layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                tweetViewModel.getTweets(callback = this@MainActivity, maxId = maxId) // FIXME
            }
        })*/
    }

    private fun configureSwipeRefreshLayout() {
        swipe_refresh_layout.run {
            setOnRefreshListener(this@MainActivity)
            setColorSchemeResources(
                R.color.primary,
                R.color.accent,
                R.color.primary_dark,
                R.color.accent
            )
        }
    }

    private fun showProfile() {
        val intent = ProfileActivity.getIntent(this, user)
        startActivity(intent)
    }

}
