package com.kbu.lib.ui.pages

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.SearchList
import com.kbu.lib.ui.search.SearchRecycler
import kotlinx.android.synthetic.main.pages_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*

class PagesFragment : BaseFragment(R.layout.pages_fragment){

    companion object {
        fun newInstance() = PagesFragment()
    }

    private val viewModel: PagesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchRecycler(arrayListOf<SearchList>(), parentFragmentManager)
        pages_Recycler.addItemDecoration(DividerItemDecoration(view.context, 1))
        pages_Recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        pages_Recycler.setHasFixedSize(true)
        pages_Recycler.adapter = adapter

    }
}