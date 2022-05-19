package com.kbu.lib.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.data.recyclerData.SearchList
import com.kbu.lib.databinding.SearchbooksCardviewBinding
import com.kbu.lib.function.FragmentChangeManager
import com.kbu.lib.ui.information.InfoBooksFragment

class SearchViewHolder(
    private val binding: SearchbooksCardviewBinding,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.ViewHolder(binding.root) {


    fun bindItem(data: SearchList) {
        Glide.with(itemView.context).load(data.img).into(binding.SearchBookimg)
        binding.searchList = SearchList(data.img, data.title, data.text, data.url)
        itemView.setOnClickListener {
            FragmentChangeManager(InfoBooksFragment(), fragmentManager).setInfoFragment(data)
        }
    }
}

class SearchRecycler(
    private val searchList: ArrayList<SearchList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun getItemCount(): Int = searchList.size

    private var listCount: Int = 2

    fun getCount() = listCount

    fun setCount() {
        listCount += 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(SearchList: SearchList) {
        searchList.add(SearchList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) = p0.bindItem(searchList[p1])


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder =
        SearchViewHolder(
            SearchbooksCardviewBinding.inflate(LayoutInflater.from(p0.context), p0, false),
            fragmentManager
        )
}