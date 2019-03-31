package com.alancamargo.tweetreader.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModel
import kotlinx.android.synthetic.main.activity_photo_details.*

class PhotoDetailsActivity : AppCompatActivity() {

    private val photo by lazy { intent.getStringExtra(EXTRA_PHOTO) }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PhotoDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        viewModel.loadPhoto(photo, img_photo) // TODO: optimise to lifecycle
        bt_close.setOnClickListener { finish() }
    }

    companion object {
        private const val EXTRA_PHOTO = "com.alancamargo.tweetreader.EXTRA_PHOTO"

        fun getIntent(context: Context, photo: String): Intent {
            return Intent(context, PhotoDetailsActivity::class.java)
                .putExtra(EXTRA_PHOTO, photo)
        }
    }

}