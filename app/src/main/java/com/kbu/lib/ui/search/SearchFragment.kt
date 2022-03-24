package com.kbu.lib.ui.search


import android.content.ContentValues.TAG
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchRecycler(arrayListOf<SearchList>(), parentFragmentManager)
        searchRecycler.addItemDecoration(DividerItemDecoration(view.context, 1))
        searchRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchRecycler.setHasFixedSize(true)
        try {
            if (viewModel.getBookTitle() == arguments?.getString("URL").toString()) {
                searchRecycler.adapter = viewModel.getAdapter()

            } else {
                makeText(context, "책을 검ㅠ색합니다.", Toast.LENGTH_LONG).show()
                searchRecycler.adapter = adapter
                viewModel.setAdapter(adapter)
                viewModel.oneListCount()
                viewModel.setBookTitle(arguments?.getString("URL").toString())
                bookListIndex(adapter)

            }
            moreBookList(viewModel.getAdapter())
        } catch (e: Exception) {
            Log.d("viewModel Error", e.toString())
        }

    }

    private fun bookListIndex(searchRecycler: SearchRecycler) {
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
                    searchRecycler.addItem(arrayList[i])
            else
                makeText(context, "항목이 없습니다.", Toast.LENGTH_LONG).show()
        }

    }

    private fun moreBookList(searchRecycler: SearchRecycler) {
        search_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                val listCount: Int = viewModel.getListCount()
                var arrayListNew = arrayListOf<SearchList>()

                makeText(context, "더 많은 책을 불러오고 있습니다.", Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        withContext(Dispatchers.Default) {
                            arrayListNew = dataManager.bookListIndex(
                                arguments?.getString("URL").toString() + "&p=$listCount"
                            )
                        }
                        for (i in arrayListNew.indices) {
                            searchRecycler.addItem(arrayListNew[i])
                        }
//                        Log.i(TAG, arrayListNew.toString());
                    } catch (e: Exception) {
                        Log.e("newBookList", "Error : $e")
                    }
                }
                makeText(context, "새로운 책을 불러왔습니다.", Toast.LENGTH_LONG).show()
                viewModel.setListCount()
            }
        })

    }
}