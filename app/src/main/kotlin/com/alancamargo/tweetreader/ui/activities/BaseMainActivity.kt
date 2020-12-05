package com.alancamargo.tweetreader.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityStateObserver
import com.alancamargo.tweetreader.ui.adapter.TweetAdapter
import com.alancamargo.tweetreader.ui.listeners.EndlessScrollListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.tools.extensions.isFirstItemVisible
import com.alancamargo.tweetreader.ui.tools.extensions.scrollToTop
import com.alancamargo.tweetreader.ui.tools.extensions.showAppInfo
import com.alancamargo.tweetreader.ui.tools.extensions.showPrivacyTerms
import com.alancamargo.tweetreader.ui.viewmodel.TweetViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_base.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

open class BaseMainActivity : AppCompatActivity(R.layout.activity_main),
    SwipeRefreshLayout.OnRefreshListener, ShareButtonClickListener {

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
            searchView?.run {
                queryHint = getString(R.string.search)
                setOnCloseListener {
                    showTweets(this@BaseMainActivity.tweets)
                    return@setOnCloseListener false
                }
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_clear_cache -> clearCacheAndShowToast()
            R.id.item_privacy -> showPrivacyTerms()
            R.id.item_about -> showAppInfo()
        }

        return true
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        loadTweets(isRefreshing = true)
    }

    override suspend fun onShareButtonClicked(tweet: Tweet) {
        when (val shareIntentResult = viewModel.getShareIntent(tweet)) {
            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    startActivity(shareIntentResult.body)
                }
            }

            else -> {
                withContext(Dispatchers.Main) {
                    Snackbar.make(
                        main_activity_root,
                        R.string.error_sharing_tweet,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun configureRecyclerView() {
        recycler_view.adapter = adapter.also {
            it.setShareButtonClickListener(this)
        }

        recycler_view.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                loadTweets(hasScrolledToBottom = true)
            }
        })
    }

    private fun loadTweets(hasScrolledToBottom: Boolean = false, isRefreshing: Boolean = false) {
        progress_bar.visibility = VISIBLE
        viewModel.getTweets(hasScrolledToBottom, isRefreshing).observe(this, {
            processResult(it)
        })
    }

    private fun configureSwipeRefreshLayout() = with(swipe_refresh_layout) {
        setOnRefreshListener(this@BaseMainActivity)
        setColorSchemeResources(
            R.color.primary,
            R.color.accent,
            R.color.primary_dark,
            R.color.accent
        )
    }

    private fun processResult(result: Result<List<Tweet>>) {
        when (result) {
            is Result.Success -> showTweets(result.body)
            is Result.NetworkError -> showDisconnectedError()
            is Result.AccountSuspendedError -> showAccountSuspendedError()
            is Result.GenericError -> showGenericError()
        }
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

    private fun showError() {
        hideProgressBars()

        recycler_view.visibility = GONE
        group_error.visibility = VISIBLE
    }

    private fun hideErrorIfVisible() {
        group_error.visibility = GONE
        recycler_view.visibility = VISIBLE
    }

    private fun getQueryListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progress_bar.visibility = VISIBLE
                    viewModel.searchTweets(query).observe(this@BaseMainActivity, {
                        processResult(it)
                    })
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        }
    }

    private fun clearCacheAndShowToast() {
        viewModel.clearCache()
        Toast.makeText(this, R.string.cache_cleared, Toast.LENGTH_SHORT).show()
    }

}
