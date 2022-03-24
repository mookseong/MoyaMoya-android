package com.kbu.lib.ui.search

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
import com.kbu.lib.ui.information.InfoBooksFragment
import kotlinx.android.synthetic.main.searchbooks_cardview.view.*


class SearchViewHolder(view: View, private val fragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(view) {
    private val fragChangeManager = FragmentChangeManager()

    fun bindItem(data: SearchList) {
        Glide.with(itemView.context).load(data.Img).into(itemView.Search_bookimg)
        itemView.Search_Title.text = data.Title
        itemView.Search_Text.text = data.Text
        itemView.setOnClickListener {
            fragChangeManager.setDataAtFragment(
                InfoBooksFragment(),
                fragmentManager,
                data.Url,
                data.Img,
                data.Title,
                data.Text
            )
        }
    }
}

class SearchRecycler(
    private val searchList: ArrayList<SearchList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun getItemCount(): Int = searchList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(SearchList: SearchList) {
        searchList.add(SearchList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        p0.bindItem(searchList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder =
        SearchViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.searchbooks_cardview, p0, false),
            fragmentManager
        )

}