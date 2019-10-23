package com.kbu.lib.Recycler

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kbu.lib.BookInformationActivity
import com.kbu.lib.R
import com.kbu.lib.data.Search
import kotlinx.android.synthetic.main.searchbooks_cardview.view.*

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(data: Search) {
        Glide.with(itemView.context).load(data.Img).into(itemView.Search_bookimg)
        itemView.Search_Title.text = data.Title
        itemView.Search_Text.text = data.Text
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, BookInformationActivity::class.java)
            intent.putExtra("URL", data.Url)
            intent.putExtra("IMG", data.Img)
            itemView.context.startActivity(intent)
        }
    }
}

class BookSearchRecycler(private val searchList: ArrayList<Search>) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun getItemCount(): Int = searchList.size


    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        p0.bindItem(searchList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder =
        SearchViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.searchbooks_cardview, p0, false))

}