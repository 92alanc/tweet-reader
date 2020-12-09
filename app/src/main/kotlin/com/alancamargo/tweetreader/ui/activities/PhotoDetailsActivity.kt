package com.alancamargo.tweetreader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.ads.ImageHandler
import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PhotoDetailsActivity : AppCompatActivity(R.layout.activity_photo_details) {

    private val imageHandler by inject<ImageHandler>()
    private val photo by lazy { intent.getStringExtra(EXTRA_PHOTO) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        lifecycleScope.launch {
            imageHandler.loadImage(photo, img_photo)
        }
        setUpCloseButton()
    }

    private fun setUpCloseButton() {
        with(bt_close) {
            bringToFront()
            setOnClickListener { finish() }
        }
    }

    companion object {
        private const val EXTRA_PHOTO = "com.alancamargo.tweetreader.EXTRA_PHOTO"

        fun getIntent(context: Context, photo: String): Intent {
            return Intent(context, PhotoDetailsActivity::class.java).putExtra(EXTRA_PHOTO, photo)
        }
    }

}