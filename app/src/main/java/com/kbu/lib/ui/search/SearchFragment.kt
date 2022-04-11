package com.kbu.lib.ui.search


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.SearchList
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.*


class SearchFragment : BaseFragment(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchRecycler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecycler.addItemDecoration(DividerItemDecoration(view.context, 1))
        searchRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchRecycler.setHasFixedSize(true)

        if (!::searchAdapter.isInitialized){
            makeText(context, "정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
            searchAdapter =  SearchRecycler(arrayListOf<SearchList>(), parentFragmentManager)
        }
        bookListIndex()
        searchRecycler.adapter = searchAdapter
        moreBookList()
    }

    private fun bookListIndex() {
        CoroutineScope(Dispatchers.Main).launch {
            var arrayList = arrayListOf<SearchList>()
            try {
                withContext(Dispatchers.Default) {
                    arrayList = dataManager.bookListIndex(arguments?.getString("URL"))
                }
            } catch (e: Exception) {
                Log.e("BookList", "Error : $e")
            }
            if (1 <= arrayList.size)
                for (i in arrayList.indices)
                    searchAdapter.addItem(arrayList[i])
            else
                makeText(context, "항목이 없습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun moreBookList() {
        search_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                makeText(context, "더 많은 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Default).launch {
                    try {
                        val arrayListNew = dataManager.bookListIndex(
                            arguments?.getString("URL")
                                .toString() + "&p=" + searchAdapter.listCount
                        )
                        withContext(Dispatchers.Main) {
                            for (i in arrayListNew.indices) {
                                searchAdapter.addItem(arrayListNew[i])
                            }
                            makeText(context, "새로운 정보를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                            searchAdapter.listCount += 1
                        }
                    } catch (e: Exception) {
                        Log.e("newBookList", "Error : $e")
                        withContext(Dispatchers.Main) {
                            makeText(context, "정보에 문제가 생겨 불러오기를 중단했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        })

    }
}