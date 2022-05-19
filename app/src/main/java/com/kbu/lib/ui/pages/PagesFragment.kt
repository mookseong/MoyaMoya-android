package com.kbu.lib.ui.pages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.databinding.PagesFragmentBinding
import com.kbu.lib.ui.search.SearchRecycler


class PagesFragment : BaseFragment<PagesFragmentBinding>(R.layout.pages_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchRecycler(arrayListOf(), parentFragmentManager)
        binding.pagesRecycler.addItemDecoration(DividerItemDecoration(view.context, 1))
        binding.pagesRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.pagesRecycler.setHasFixedSize(true)
        binding.pagesRecycler.adapter = adapter
    }

    override fun initView() {
        TODO("Not yet implemented")
    }
}