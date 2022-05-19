package com.kbu.lib.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.data.recyclerData.MainViewBookList
import com.kbu.lib.databinding.MainBookCardviewBinding
import com.kbu.lib.ui.main.BookViewHolder

class BaseRecycler {


}
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
