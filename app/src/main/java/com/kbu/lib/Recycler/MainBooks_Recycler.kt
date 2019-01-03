package com.kbu.lib.Recycler


import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kbu.lib.BookInformationActivity
import com.kbu.lib.R
import com.kbu.lib.data.Mainbook
import kotlinx.android.synthetic.main.books_cardview.view.*

class BookViewHolder(view : View) : RecyclerView.ViewHolder(view){
    fun bindItem(data: Mainbook) {
        Glide.with(itemView.context).load(data.Img).into(itemView.bookimg)
        itemView.setOnClickListener {
            val BookInformation = Intent(itemView.context, BookInformationActivity::class.java)
            BookInformation.putExtra("URL", data.URL)
            BookInformation.putExtra("IMG", data.Img)
            itemView.context.startActivity(BookInformation)
        }
    }
}



class MainBooks(val booklist : ArrayList<Mainbook>) : RecyclerView.Adapter<BookViewHolder>(){
    override fun getItemCount() = booklist.size

    override fun onBindViewHolder(p0: BookViewHolder, p1: Int) {
        p0.bindItem(booklist[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookViewHolder =
        BookViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.books_cardview, p0, false))
}