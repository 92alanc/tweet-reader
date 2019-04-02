package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.PhotoDetailsActivity
import com.alancamargo.tweetreader.util.setImageUrl

class ViewPagerAdapter(private val photos: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(container.context)
            .inflate(R.layout.item_photo, container, false)
            .apply {
                val imageView = findViewById<ImageView>(R.id.photo)
                setImageUrl(imageView, photos[position])
                container.addView(imageView)
                imageView.setOnClickListener {
                    val context = it.context
                    val intent = PhotoDetailsActivity.getIntent(context, photos[position])
                    context.startActivity(intent)
                }
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