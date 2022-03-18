package com.kbu.lib.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.SearchList
import kotlinx.android.synthetic.main.searchbooks_cardview.view.*


class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(data: SearchList) {
        Glide.with(itemView.context).load(data.Img).into(itemView.Search_bookimg)
        itemView.Search_Title.text = data.Title
        itemView.Search_Text.text = data.Text
        itemView.setOnClickListener {

        }
    }
}

class SearchRecycler(private val searchList: ArrayList<SearchList>) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun getItemCount(): Int = searchList.size


    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        p0.bindItem(searchList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder =
        SearchViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.searchbooks_cardview, p0, false))

}