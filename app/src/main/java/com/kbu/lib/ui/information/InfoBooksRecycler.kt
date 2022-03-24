package com.kbu.lib.ui.information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.data.Information
import kotlinx.android.synthetic.main.info_borrow_cardview.view.*


class InformationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(data: Information) {
        itemView.number.text = data.registration_number
        itemView.callnumber.text = data.callnumbers
        itemView.possession.text = data.possession_position
        itemView.rental.text = data.rental
    }
}

class InfoBooksRecycler(private val information: ArrayList<Information>) :
    RecyclerView.Adapter<InformationViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(Information: Information) {
        information.add(Information)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = information.size

    override fun onBindViewHolder(p0: InformationViewHolder, p1: Int) {
        p0.bindItem(information[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InformationViewHolder =
        InformationViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.info_borrow_cardview, p0, false)
        )

}
