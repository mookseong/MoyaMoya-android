package com.kbu.lib.ui.main


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.data.recyclerData.MainViewBookList
import com.kbu.lib.databinding.MainBookCardviewBinding
import com.kbu.lib.function.FragmentChangeManager
import com.kbu.lib.ui.information.InfoBooksFragment

class BooksMainRecycler(
    private val mainViewBookList: ArrayList<MainViewBookList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<BookViewHolder>() {
    override fun getItemCount() = mainViewBookList.size

    //데이터 값을 넣고 갱신한다.
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
            MainBookCardviewBinding.inflate(LayoutInflater.from(p0.context), p0, false),
            fragmentManager
        )
}

class BookViewHolder(
    private val binding: MainBookCardviewBinding,
    private val fragmentManager: FragmentManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(data: MainViewBookList) {
        Glide.with(itemView.context).load(data.img).into(binding.listBookImg)
        itemView.setOnClickListener {
            FragmentChangeManager(InfoBooksFragment(), fragmentManager).setInfoFragment(data)
        }
    }
}