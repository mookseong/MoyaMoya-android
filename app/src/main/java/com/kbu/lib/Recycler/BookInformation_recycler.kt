package com.kbu.lib.Recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kbu.lib.R
import com.kbu.lib.data.Information
import kotlinx.android.synthetic.main.possession_cardview.view.*

class InformationViewHolder(view : View) : RecyclerView.ViewHolder(view){
    fun bindItem(data : Information){
        itemView.number.text = data.registration_number
        itemView.callnumber.text = data.callnumbers
        itemView.possession.text = data.possession_position
        itemView.rental.text = data.rental
    }
}

class BookInformation_recycler(val Information: ArrayList<Information>) : RecyclerView.Adapter<InformationViewHolder>() {
    override fun getItemCount(): Int  = Information.size

    override fun onBindViewHolder(p0: InformationViewHolder, p1: Int) {
        p0.bindItem(Information[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InformationViewHolder =
        InformationViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.possession_cardview, p0, false))

}
