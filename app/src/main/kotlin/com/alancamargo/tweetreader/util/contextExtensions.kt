package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.model.api.OAuth2Token
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.getColour(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.callApi(func: (token: String, api: TwitterApi) -> Unit) {
    val preferenceHelper = PreferenceHelper(this)
    val api = TwitterApi.getService()

    if (preferenceHelper.getAccessToken().isEmpty()) {
        val credentials = Credentials.basic(
            BuildConfig.CONSUMER_KEY,
            BuildConfig.CONSUMER_SECRET
        )
        api.postCredentials(credentials).enqueue(object :
            Callback<OAuth2Token> {
            override fun onResponse(call: Call<OAuth2Token>, response: Response<OAuth2Token>) {
                response.body()?.let {
                    preferenceHelper.setAccessToken(it.getAuthorisationHeader())
                    func(it.getAuthorisationHeader(), api)
                }
            }

            override fun onFailure(call: Call<OAuth2Token>, t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
            }
        })
    } else {
        func(preferenceHelper.getAccessToken(), api)
    }
}

fun Context.getVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Context.getAppName(): String = getString(R.string.app_name)
