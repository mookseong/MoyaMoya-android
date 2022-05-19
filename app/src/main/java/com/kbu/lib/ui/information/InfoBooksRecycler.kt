package com.kbu.lib.ui.information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.data.recyclerData.InfoBook
import com.kbu.lib.databinding.InfoBooksFragmentBinding
import com.kbu.lib.databinding.InfoBorrowCardviewBinding


class InformationViewHolder(private val binding: InfoBorrowCardviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindItem(data: InfoBook) {
        binding.infoBook =
            InfoBook(data.registerNumber, data.callNumber, data.ownPosition, data.rental)
    }
}

class InfoBooksRecycler(private val infoBooks: ArrayList<InfoBook>) :
    RecyclerView.Adapter<InformationViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(InfoBook: InfoBook) {
        infoBooks.add(InfoBook)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = infoBooks.size

    override fun onBindViewHolder(p0: InformationViewHolder, p1: Int) =
        p0.bindItem(infoBooks[p1])

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InformationViewHolder =
        InformationViewHolder(
            InfoBorrowCardviewBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        )
}
