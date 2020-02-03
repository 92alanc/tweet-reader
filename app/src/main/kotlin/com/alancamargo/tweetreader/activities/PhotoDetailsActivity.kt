package com.alancamargo.tweetreader.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModel
import kotlinx.android.synthetic.main.activity_photo_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class PhotoDetailsActivity : AppCompatActivity(R.layout.activity_photo_details) {

    private val viewModel by viewModel<PhotoDetailsViewModel>()
    private val photo by lazy { intent.getStringExtra(EXTRA_PHOTO) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        viewModel.loadPhoto(photo, img_photo)
        setUpCloseButton()
    }

    private fun setUpCloseButton() {
        bt_close.run {
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