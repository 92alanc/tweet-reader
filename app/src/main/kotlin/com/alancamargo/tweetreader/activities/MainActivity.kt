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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.EndlessScrollListener
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.repository.TwitterCallback
import com.alancamargo.tweetreader.util.loadAnnoyingAds
import com.alancamargo.tweetreader.util.watchConnectivityState
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.alancamargo.tweetreader.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),
    TwitterCallback,
    Observer<List<Tweet>>,
    SwipeRefreshLayout.OnRefreshListener,
    CoroutineScope {

    private val adapter = TweetAdapter()
    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    private val tweetViewModel by lazy {
        ViewModelProviders.of(this).get(TweetViewModel::class.java)
    }

    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    private val layoutManager by lazy { recycler_view.layoutManager as LinearLayoutManager }

    private lateinit var user: User
    private var menu: Menu? = null
    private var tweets: List<Tweet> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.title)
        configureRecyclerView()
        tweetViewModel.getTweets(lifecycleOwner = this, callback = this)
        userViewModel.getUserDetails(lifecycleOwner = this, callback = this)
        configureSwipeRefreshLayout()
        progress_bar.visibility = VISIBLE
        ad_view.loadAnnoyingAds()
        watchConnectivityState(snackbarView = ad_view)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBackPressed() {
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val isOnTop = firstVisibleItemPosition == 0

        if (isOnTop) {
            super.onBackPressed()
        } else {
            recycler_view.scrollToPosition(0)
        }
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
        launch {
            userDetails.observe(this@MainActivity, Observer<User?> {
                it?.let { user ->
                    this@MainActivity.user = user
                }
            })

            withContext(Dispatchers.Default) {
                userViewModel.insert(user)
            }
        }
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

    override fun onChanged(tweets: List<Tweet>?) {
        launch {
            if (swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = false
            }

            tweets?.let {
                withContext(Dispatchers.Default) {
                    it.forEach { tweet ->
                        tweetViewModel.insert(tweet)
                    }
                }
                this@MainActivity.tweets = this@MainActivity.tweets.union(it).toList()
                progress_bar.visibility = GONE
                adapter.submitList(this@MainActivity.tweets)
            }
        }
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        val sinceId = if (tweets.isEmpty()) null else tweets.first().id
        tweetViewModel.getTweets(lifecycleOwner = this, callback = this, sinceId = sinceId)
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object :
            EndlessScrollListener() {
            override fun onLoadMore() {
                val maxId = if (tweets.isEmpty()) null else tweets.last().id - 1
                tweetViewModel.getTweets(
                    lifecycleOwner = this@MainActivity,
                    callback = this@MainActivity,
                    maxId = maxId
                )
            }
        })
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
