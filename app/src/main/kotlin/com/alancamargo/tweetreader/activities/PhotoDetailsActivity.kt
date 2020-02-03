package com.alancamargo.tweetreader.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModel
import com.alancamargo.tweetreader.viewmodel.PhotoDetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_photo_details.*
import org.koin.android.ext.android.inject

class PhotoDetailsActivity : AppCompatActivity(R.layout.activity_photo_details) {

    private val photo by lazy { intent.getStringExtra(EXTRA_PHOTO) }
    private val viewModelFactory by inject<PhotoDetailsViewModelFactory>()

    private lateinit var viewModel: PhotoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        setUpViewModel()
        viewModel.loadPhoto(photo, img_photo)
        setUpCloseButton()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            PhotoDetailsViewModel::class.java
        )
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
            return Intent(context, PhotoDetailsActivity::class.java)
                .putExtra(EXTRA_PHOTO, photo)
        }
    }

}