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

    private lateinit var searchAdapter: SearchRecycler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecycler.addItemDecoration(DividerItemDecoration(view.context, 1))
        searchRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchRecycler.setHasFixedSize(true)

        //데이터가 있는 확인하고 없으면 어뎁터를 초기화를 진행해준다
        if (!::searchAdapter.isInitialized){
            makeText(context, "정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
            searchAdapter =  SearchRecycler(arrayListOf<SearchList>(), parentFragmentManager)
            bookListIndex()
        }
        searchRecycler.adapter = searchAdapter
        moreBookList() //리스트가 맨 마지막에 있다면 자동으로 책을 더불러 온다.
    }

    //책 리스트를 불러와 사용자에게 표시해준다.
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
            //리스트 항목을 확인하고 없다면 메세지 출력
            if (1 <= arrayList.size)
                for (i in arrayList.indices)
                    searchAdapter.addItem(arrayList[i])
            else
                makeText(context, "항목이 없습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    //리스트가 맨 마지막에 있다면 자동으로 책을 더불러 온다.
    private fun moreBookList() {
        //사용자 스크롤 위치가 맨아래에 있다면 이벤트를 확인하고 자동으로 불러온다.
        search_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                makeText(context, "더 많은 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Default).launch {
                    try {
                        //데이터를 불러오고 변수에 저장한다.
                        val arrayListNew = dataManager.bookListIndex(
                            arguments?.getString("URL")
                                .toString() + "&p=" + searchAdapter.listCount
                        )
                        withContext(Dispatchers.Main) {
                            //데이터를 Recyclerview에 넣어준다.
                            for (i in arrayListNew.indices) {
                                searchAdapter.addItem(arrayListNew[i])
                            }
                            makeText(context, "새로운 정보를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                            searchAdapter.listCount += 1 //북리스트르 페이지를 +1를 하여 다음 페이지 위치 지정
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