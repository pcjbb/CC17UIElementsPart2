package com.example.cc17uielementspart2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ImageAdapter(val c: Context) : BaseAdapter() {
    private val mContext: Context

    init {
        mContext = c
    }

    var thumbImages = arrayOf<Int>(
        R.drawable.kidkrow,
        R.drawable.nswy,
        R.drawable.odaat
    )

    override fun getCount(): Int {
        return thumbImages.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(mContext)
        imageView.layoutParams = AbsListView.LayoutParams(500, 500)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setPadding(10,10,10,10)
        imageView.setImageResource(thumbImages[position])
        return imageView
    }

}

