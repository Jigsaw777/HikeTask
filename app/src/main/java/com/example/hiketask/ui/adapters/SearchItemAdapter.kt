package com.example.hiketask.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hiketask.R
import com.example.hiketask.domain.entities.PhotoEntity
import com.example.hiketask.ui.viewholders.SearchItemVH

class SearchItemAdapter(private val context: Context) : RecyclerView.Adapter<SearchItemVH>() {

    private var searchItems = mutableListOf<PhotoEntity>()
    private var size=0

    fun setAttributes(items: List<PhotoEntity>) {
        size=searchItems.size
        searchItems.addAll(items.toMutableList())
        notifyDataSetChanged()
//        notifyItemRangeInserted(size,searchItems.size)
    }

    fun isEmpty():Boolean{
        return searchItems.isEmpty()
    }

    fun clear(){
        searchItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemVH {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false)
        return SearchItemVH(view)
    }

    override fun getItemCount(): Int = searchItems.size

    override fun onBindViewHolder(holder: SearchItemVH, position: Int) {
        holder.bindVH(searchItems[position])
    }

}