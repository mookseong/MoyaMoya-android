package com.kbu.lib.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.MainViewBookList
import kotlinx.android.synthetic.main.books_cardview.view.*

class BooksMainRecycler(private val mainViewBookList : ArrayList<MainViewBookList>) : RecyclerView.Adapter<BookViewHolder>(){
    override fun getItemCount() = mainViewBookList.size

    override fun onBindViewHolder(p0: BookViewHolder, p1: Int) {
        p0.bindItem(mainViewBookList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookViewHolder =
        BookViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.books_cardview, p0, false))
}

class BookViewHolder(view : View) : RecyclerView.ViewHolder(view){
    fun bindItem(data: MainViewBookList) {
        Glide.with(itemView.context).load(data.Img).into(itemView.bookImg)
    }
}