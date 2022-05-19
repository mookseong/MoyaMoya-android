package com.kbu.lib.ui.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.data.recyclerData.SearchList
import com.kbu.lib.databinding.SearchbooksCardviewBinding
import com.kbu.lib.function.FragmentChangeManager
import com.kbu.lib.ui.information.InfoBooksFragment


class PagesViewHolder(view: View, fragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(view) {
    private val fragChangeManager = FragmentChangeManager(InfoBooksFragment(), fragmentManager)
    private lateinit var binding: SearchbooksCardviewBinding

    fun bindItem(data: SearchList) {
        Glide.with(itemView.context).load(data.img).into(binding.SearchBookimg)
        binding.SearchTitle.text = data.title
        binding.SearchText.text = data.text
    }
}

class PagesRecycle(
    private val pagesList: ArrayList<SearchList>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<PagesViewHolder>() {
    override fun getItemCount(): Int = pagesList.size

    override fun onBindViewHolder(p0: PagesViewHolder, p1: Int) {
        p0.bindItem(pagesList[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PagesViewHolder =
        PagesViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.searchbooks_cardview, p0, false),
            fragmentManager
        )

}