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

    private var user: User? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        configureRecyclerView()
        loadTweets()
        configureSwipeRefreshLayout()
        ad_view.loadBannerAds()
    }

    override fun onBackPressed() {
        val layoutManager = recycler_view.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val isAtTop = firstVisibleItemPosition == 0

        if (isAtTop)
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
        loadTweets(isRefreshing = true)
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                loadTweets(hasScrolledToBottom = true)
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

    private fun loadTweets(hasScrolledToBottom: Boolean = false, isRefreshing: Boolean = false) {
        progress_bar.visibility = VISIBLE
        viewModel.getTweets(hasScrolledToBottom, isRefreshing).observe(this, Observer {
            if (it.isEmpty())
                showDisconnectedMessage()
            else
                showTweets(it)
        })
    }

    private fun showTweets(tweets: List<Tweet>) {
        hideProgressBars()
        hideDisconnectedMessage()
        updateTweets(tweets)
    }

    private fun updateTweets(tweets: List<Tweet>) {
        if (user == null)
            user = tweets.firstOrNull()?.author

        adapter.submitList(tweets)
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

}
