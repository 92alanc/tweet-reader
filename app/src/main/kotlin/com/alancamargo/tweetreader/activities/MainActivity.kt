package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.EndlessScrollListener
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.append
import com.alancamargo.tweetreader.util.loadBannerAds
import com.alancamargo.tweetreader.util.showAppInfo
import com.alancamargo.tweetreader.util.showPrivacyTerms
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.crashlytics.android.Crashlytics
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main),
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter by inject<TweetAdapter>()
    private val viewModel by viewModel<TweetViewModel>()
    private val layoutManager by lazy { recycler_view.layoutManager as LinearLayoutManager }

    private var user: User? = null
    private var menu: Menu? = null
    private var tweets: List<Tweet> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        configureRecyclerView()
        loadTweets()
        configureSwipeRefreshLayout()
        progress_bar.visibility = VISIBLE
        ad_view.loadBannerAds()
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
        val sinceId = tweets.getSinceId()
        loadTweets(sinceId = sinceId, isRefreshing = true)
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object :
            EndlessScrollListener() {
            override fun onLoadMore() {
                val maxId = tweets.getMaxId()
                loadTweets(maxId = maxId, isRefreshing = false)
            }
        })
    }

    private fun configureSwipeRefreshLayout() = with(swipe_refresh_layout) {
        setOnRefreshListener(this@MainActivity)
        setColorSchemeResources(
            R.color.primary,
            R.color.accent,
            R.color.primary_dark,
            R.color.accent
        )
    }

    private fun loadTweets(
        maxId: Long? = null,
        sinceId: Long? = null,
        isRefreshing: Boolean = false
    ) {
        viewModel.getTweets(maxId, sinceId).observe(this, Observer {
            if (it.isEmpty() && noTweetsLoaded())
                showDisconnectedMessage()
            else if (it.isEmpty())
                hideProgressBars()
            else
                showTweets(it, isRefreshing)
        })
    }

    private fun showTweets(tweets: List<Tweet>, isRefreshing: Boolean) {
        hideProgressBars()
        hideDisconnectedMessage()
        updateTweets(tweets, isRefreshing)
    }

    private fun updateTweets(tweets: List<Tweet>, isRefreshing: Boolean) {
        if (user == null)
            user = tweets.firstOrNull()?.author

        if (isRefreshing)
            this.tweets = tweets.append(this.tweets)
        else
            this.tweets = this.tweets.append(tweets)

        adapter.submitList(this.tweets)
    }

    private fun hideProgressBars() {
        progress_bar.visibility = GONE

        if (swipe_refresh_layout.isRefreshing)
            swipe_refresh_layout.isRefreshing = false
    }

    private fun showDisconnectedMessage() {
        hideProgressBars()

        menu?.findItem(R.id.item_profile)?.let { item ->
            item.isVisible = false
        }

        recycler_view.visibility = GONE
        group_disconnected.visibility = VISIBLE
    }

    private fun hideDisconnectedMessage() {
        menu?.findItem(R.id.item_profile)?.let { item ->
            item.isVisible = true
        }

        group_disconnected.visibility = GONE
        recycler_view.visibility = VISIBLE
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

    private fun noTweetsLoaded() = this.tweets.isEmpty()

    private fun List<Tweet>.getMaxId(): Long? {
        return if (isEmpty())
            null
        else
            last().id - 1
    }

    private fun List<Tweet>.getSinceId(): Long? {
        return if (isEmpty())
            null
        else
            first().id
    }

}
