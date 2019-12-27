package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.EndlessScrollListener
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.*
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import com.alancamargo.tweetreader.viewmodel.View
import com.crashlytics.android.Crashlytics
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener,
    CoroutineScope,
    View {

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
        launch {
            tweetViewModel.getTweets().observe(this@MainActivity, Observer { tweets ->
                onTweetsFound(tweets, isRefreshing = false)
            })
        }
        configureSwipeRefreshLayout()
        progress_bar.visibility = VISIBLE
        ad_view.loadBannerAds()
        watchConnectivityState(snackbarView = ad_view)
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

    override fun onTweetsFound(tweets: List<Tweet>, isRefreshing: Boolean) {
        Log.d(javaClass.simpleName, "tweets: ${tweets.size}")
        hideProgressBars()
        updateTweets(tweets, isRefreshing)
    }

    override fun onNothingFound() {
        hideProgressBars()
    }

    override fun onAccountSuspended() {
        hideProgressBars()

        menu?.findItem(R.id.item_profile)?.let { item ->
            item.isVisible = false
        }

        recycler_view.visibility = GONE
        group_account_suspended.visibility = VISIBLE
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        val sinceId = if (tweets.isEmpty()) null else tweets.first().id
        launch {
            tweetViewModel.getTweets(sinceId = sinceId).observe(this@MainActivity, Observer { tweets ->
                onTweetsFound(tweets, isRefreshing = true)
            })
        }
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object :
            EndlessScrollListener() {
            override fun onLoadMore() {
                val maxId = if (tweets.isEmpty()) null else tweets.last().id - 1
                launch {
                    tweetViewModel.getTweets(maxId = maxId).observe(this@MainActivity, Observer {
                        onTweetsFound(it, isRefreshing = true)
                    })
                }
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

    private fun showAppInfo(): Boolean {
        val title = "${getString(R.string.app_name)} ${getVersionName()}"
        val rawText = getString(R.string.developer_info)
        val textToHighlight = rawText.split("\n\n").last()
        val message = rawText.toSpannable()
            .bold(textToHighlight)
            .colour(textToHighlight, getColour(R.color.red))

        AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.ok, null)
            .show()

        return true
    }

    private fun showPrivacyTerms(): Boolean {
        val appName = getAppName()
        val message = getString(R.string.privacy_terms_format, appName, appName)

        AlertDialog.Builder(this)
            .setTitle(R.string.privacy_terms)
            .setMessage(message)
            .setNeutralButton(R.string.ok, null)
            .show()
        return true
    }

}
