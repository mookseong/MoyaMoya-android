package com.kbu.lib.Recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.Mainbook
import kotlinx.android.synthetic.main.books_cardview.view.*

class ViewHolder(viewid : View) : RecyclerView.ViewHolder(viewid){
    fun bindItem(data: Mainbook) {
        Glide.with(itemView.context).load(data.Img).into(itemView.bookimg)
    }
}
class MainBooks(val booklist : ArrayList<Mainbook>) : RecyclerView.Adapter<ViewHolder>(){
    override fun getItemCount() = booklist.size
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(booklist[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.books_cardview, p0, false))
}