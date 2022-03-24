package com.kbu.lib.ui.pages

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.SearchList
import com.kbu.lib.function.FragmentChangeManager
import kotlinx.android.synthetic.main.searchbooks_cardview.view.*


class PagesViewHolder(view: View, private val fragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(view) {
    private val fragChangeManager = FragmentChangeManager()

    fun bindItem(data: SearchList) {
        Glide.with(itemView.context).load(data.Img).into(itemView.Search_bookimg)
        itemView.Search_Title.text = data.Title
        itemView.Search_Text.text = data.Text
    }
}

class PagesRecycle(
    private val pagesList: ArrayList<SearchList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<PagesViewHolder>() {
    override fun getItemCount(): Int = pagesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(SearchList: SearchList) {
        pagesList.add(SearchList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: PagesViewHolder, p1: Int) {
        p0.bindItem(pagesList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PagesViewHolder =
        PagesViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.searchbooks_cardview, p0, false),
            fragmentManager
        )

}