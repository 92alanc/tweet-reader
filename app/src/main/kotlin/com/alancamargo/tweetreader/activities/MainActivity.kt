package com.alancamargo.tweetreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.EndlessScrollListener
import com.alancamargo.tweetreader.adapter.TweetAdapter
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.device.ConnectivityStateObserver
import com.alancamargo.tweetreader.util.extensions.*
import com.alancamargo.tweetreader.viewmodel.TweetViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main),
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter by inject<TweetAdapter>()
    private val connectivityStateObserver by inject<ConnectivityStateObserver>()
    private val viewModel by viewModel<TweetViewModel>()

    private var searchView: SearchView? = null
    private var tweets = emptyList<Tweet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        configureRecyclerView()
        loadTweets()
        configureSwipeRefreshLayout()
        connectivityStateObserver.observeConnectivityState(this, main_activity_root)
        ad_view.loadBannerAds()
    }

    override fun onBackPressed() {
        if (recycler_view.isFirstItemVisible())
            super.onBackPressed()
        else
            recycler_view.scrollToTop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.item_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.setOnCloseListener {
                showTweets(this.tweets)
                return@setOnCloseListener false
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
            processResult(it)
        })
    }

    private fun showTweets(tweets: List<Tweet>) {
        hideProgressBars()
        hideErrorIfVisible()

        if (tweets.isEmpty()) {
            showNoResultsMessage()
        } else {
            this.tweets = tweets
            adapter.submitList(tweets)
        }

        searchView?.setOnQueryTextListener(getQueryListener())
    }

    private fun hideProgressBars() {
        progress_bar.visibility = GONE

        if (swipe_refresh_layout.isRefreshing)
            swipe_refresh_layout.isRefreshing = false
    }

    private fun showNoResultsMessage() {
        img_error.setImageResource(R.drawable.ic_no_results)
        txt_error.setText(R.string.message_no_results)
        showError()
    }

    private fun showDisconnectedError() {
        img_error.setImageResource(R.drawable.ic_disconnected)
        txt_error.setText(R.string.message_disconnected)
        showError()
    }

    private fun showAccountSuspendedError() {
        img_error.setImageResource(R.drawable.ic_account_suspended)
        txt_error.setText(R.string.message_account_suspended)
        showError()
    }

    private fun showGenericError() {
        img_error.setImageResource(R.drawable.ic_error)
        txt_error.setText(R.string.message_generic_error)
        showError()
    }

    private fun showError() {
        hideProgressBars()

        recycler_view.visibility = GONE
        group_error.visibility = VISIBLE
    }

    private fun hideErrorIfVisible() {
        group_error.visibility = GONE
        recycler_view.visibility = VISIBLE
    }

    private fun processResult(result: Result<List<Tweet>>) {
        when (result) {
            is Result.Success -> showTweets(result.body)
            is Result.NetworkError -> showDisconnectedError()
            is Result.AccountSuspendedError -> showAccountSuspendedError()
            is Result.GenericError -> showGenericError()
        }
    }

    private fun getQueryListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progress_bar.visibility = VISIBLE
                    viewModel.searchTweets(query).observe(this@MainActivity, Observer {
                        processResult(it)
                    })
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        }
    }

}
