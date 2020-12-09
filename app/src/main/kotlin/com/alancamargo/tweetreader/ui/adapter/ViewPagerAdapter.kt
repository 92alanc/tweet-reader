package com.alancamargo.tweetreader.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.activities.PhotoDetailsActivity
import com.alancamargo.tweetreader.ui.ads.ImageHandler
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewPagerAdapter(
    private val photos: List<String>,
    private val imageHandler: ImageHandler
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(container.context)
            .inflate(R.layout.item_photo, container, false)
            .apply {
                val indicator = findViewById<MaterialTextView>(R.id.txt_indicator)
                if (count > 1) {
                    indicator.text = context.getString(R.string.photo_indicator_format,
                        position + 1,
                        photos.size)
                } else {
                    indicator.visibility = View.GONE
                }

                val imageView = findViewById<ImageView>(R.id.photo)

                CoroutineScope(Dispatchers.Main).launch {
                    imageHandler.loadImage(photos[position], imageView)
                }

                imageView.setOnClickListener {
                    val context = it.context
                    val intent = PhotoDetailsActivity.getIntent(context, photos[position])
                    context.startActivity(intent)
                }

                container.addView(this)
            }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "${position + 1}/${photos.size}"
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = photos.size

}