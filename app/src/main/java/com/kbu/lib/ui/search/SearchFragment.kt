package com.kbu.lib.ui.search

import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.recyclerData.SearchList
import com.kbu.lib.databinding.SearchFragmentBinding
import com.kbu.lib.function.SearchFunction

class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    private lateinit var searchAdapter: SearchRecycler
    private val searchFunction = SearchFunction()
    private var url: String = ""

    override fun initView() {
        super.initView()
        url = arguments?.getString("URL").toString()
        //데이터가 있는 확인하고 없으면 어뎁터를 초기화를 진행해준다
        if (!::searchAdapter.isInitialized) {
            makeText(context, "정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
            searchAdapter = SearchRecycler(arrayListOf<SearchList>(), parentFragmentManager)
            searchFunction.listIndex(url, searchAdapter, requireContext())
        }
    }

    override fun initListener() {
        super.initListener()
        //사용자 스크롤 위치가 맨아래에 있다면 이벤트를 확인하고 자동으로 불러온다.
        binding.searchScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                makeText(context, "더 많은 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
                searchFunction.listUp(url, searchAdapter, requireContext())
            }
        })
    }

    override fun afterViewCreated() {
        super.afterViewCreated()
        binding.searchRecycler.adapter = searchAdapter
        binding.searchRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.searchRecycler.setHasFixedSize(true)
        binding.searchRecycler.addItemDecoration(DividerItemDecoration(context, 1))
    }
}