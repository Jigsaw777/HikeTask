package com.example.hiketask.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiketask.R
import com.example.hiketask.domain.entities.PhotoEntity
import kotlinx.android.synthetic.main.search_item.view.*

class SearchItemVH(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindVH(photoEntity: PhotoEntity) {
        view.search_item_text.text = photoEntity.title
        val imageUrl="http://farm${photoEntity.farm}.static.flickr.com/${photoEntity.server}/${photoEntity.id}_${photoEntity.secret}.jpg"
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .into(view.search_item_image)
    }
}