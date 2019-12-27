package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.EndlessScrollListener
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.loadBannerAds
import com.alancamargo.tweetreader.util.showAppInfo
import com.alancamargo.tweetreader.util.showPrivacyTerms
import com.alancamargo.tweetreader.util.watchConnectivityState
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.crashlytics.android.Crashlytics
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, CoroutineScope {

    private val job = Job()

    override val coroutineContext = job + Dispatchers.Main

    private val adapter = TweetAdapter()

    private val tweetViewModel by lazy {
        ViewModelProviders.of(this).get(TweetViewModel::class.java)
    }

    private val layoutManager by lazy { recycler_view.layoutManager as LinearLayoutManager }

    private var user: User? = null
    private var menu: Menu? = null
    private var tweets: List<Tweet> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.title)
        configureRecyclerView()
        loadTweets()
        configureSwipeRefreshLayout()
        progress_bar.visibility = VISIBLE
        ad_view.loadBannerAds()
        watchConnectivityState(snackbarView = ad_view)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onBackPressed() {
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val isOnTop = firstVisibleItemPosition == 0

        if (isOnTop)
            super.onBackPressed()
        else
            recycler_view.smoothScrollToPosition(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_profile -> showProfile()
            R.id.item_about -> showAppInfo()
            R.id.item_privacy -> showPrivacyTerms()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        val sinceId = if (tweets.isEmpty()) null else tweets.first().id
        loadTweets(sinceId = sinceId, isRefreshing = true)
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object :
            EndlessScrollListener() {
            override fun onLoadMore() {
                val maxId = if (tweets.isEmpty()) null else tweets.last().id - 1
                loadTweets(maxId = maxId, isRefreshing = true)
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

    private fun loadTweets(
        maxId: Long? = null,
        sinceId: Long? = null,
        isRefreshing: Boolean = false
    ) {
        launch {
            tweetViewModel.getTweets(maxId, sinceId).observe(this@MainActivity, Observer {
                showTweets(it, isRefreshing)
            })
        }
    }

    private fun showTweets(tweets: List<Tweet>, isRefreshing: Boolean) {
        hideProgressBars()
        updateTweets(tweets, isRefreshing)
    }

    private fun updateTweets(tweets: List<Tweet>, isRefreshing: Boolean) {
        if (user == null)
            user = tweets.firstOrNull()?.author

        if (isRefreshing)
            this.tweets = tweets.union(this.tweets).toList()
        else
            this.tweets = this.tweets.union(tweets).toList()

        adapter.submitList(this.tweets)
    }

    private fun hideProgressBars() {
        progress_bar.visibility = GONE

        if (swipe_refresh_layout.isRefreshing)
            swipe_refresh_layout.isRefreshing = false
    }

    // TODO
    private fun onAccountSuspended() {
        hideProgressBars()

        menu?.findItem(R.id.item_profile)?.let { item ->
            item.isVisible = false
        }

        recycler_view.visibility = GONE
        group_account_suspended.visibility = VISIBLE
    }

    private fun showProfile(): Boolean {
        if (user != null) {
            val intent = ProfileActivity.getIntent(this, user!!)
            startActivity(intent)
        } else {
            Crashlytics.log("Null user on menu item click")
            Snackbar.make(ad_view, R.string.no_data_found, Snackbar.LENGTH_SHORT).show()
        }
        return true
    }

}
