package com.kbu.lib.ui.main


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.MainViewBookList
import com.kbu.lib.data.SearchList
import com.kbu.lib.function.FragmentChangeManager
import com.kbu.lib.ui.information.InfoBooksFragment
import kotlinx.android.synthetic.main.main_book_cardview.view.*

class BooksMainRecycler(
    private val mainViewBookList: ArrayList<MainViewBookList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<BookViewHolder>() {
    override fun getItemCount() = mainViewBookList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(MainViewBookList: MainViewBookList) {
        mainViewBookList.add(MainViewBookList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: BookViewHolder, p1: Int) {
        p0.bindItem(mainViewBookList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookViewHolder =
        BookViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.main_book_cardview, p0, false),
            fragmentManager
        )
}

class BookViewHolder(view: View, private val fragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(view) {
    private val fragChangeManager = FragmentChangeManager()

    fun bindItem(data: MainViewBookList) {
        Glide.with(itemView.context).load(data.Img).into(itemView.listBookImg)

        itemView.setOnClickListener {
            fragChangeManager.setDataAtFragment(
                InfoBooksFragment(),
                fragmentManager,
                data.URL,
                data.Img
            )
        }
    }
}