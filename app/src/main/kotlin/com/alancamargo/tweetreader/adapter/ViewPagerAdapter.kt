package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.PhotoDetailsActivity
import com.alancamargo.tweetreader.util.ImageHandler

class ViewPagerAdapter(
    private val photos: List<String>,
    private val imageHandler: ImageHandler
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(container.context)
            .inflate(R.layout.item_photo, container, false)
            .apply {
                val indicator = findViewById<TextView>(R.id.txt_indicator)
                if (count > 1) {
                    indicator.text = context.getString(R.string.photo_indicator_format,
                        position + 1,
                        photos.size)
                } else {
                    indicator.visibility = View.GONE
                }

                val imageView = findViewById<ImageView>(R.id.photo)
                imageHandler.loadImage(photos[position], imageView)
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

    override fun getPageTitle(position: Int): CharSequence? {
        return "${position + 1}/${photos.size}"
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = photos.size

}