package com.kbu.lib.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.MainViewBookList
import com.kbu.lib.ui.search.SearchFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main


class MainFragment : BaseFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var newBookListAdapter: BooksMainRecycler
    private lateinit var learningBookListAdapter: BooksMainRecycler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //데이터가 있는 확인하고 없으면 어뎁터를 초기화를 진행해준다
        if (!::newBookListAdapter.isInitialized && !::learningBookListAdapter.isInitialized) {
            newBookListAdapter =
                BooksMainRecycler(arrayListOf<MainViewBookList>(), parentFragmentManager)
            learningBookListAdapter =
                BooksMainRecycler(arrayListOf<MainViewBookList>(), parentFragmentManager)
            listStartUp()
        }

        //recyclerview어뎁터 설정
        newBookList.adapter = newBookListAdapter
        learningBookList.adapter = learningBookListAdapter
        newBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        learningBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        newBookList.setHasFixedSize(true)
        learningBookList.setHasFixedSize(true)

        //버튼 클릭 이벤트
        backGround.setOnClickListener {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(bookSearch.windowToken, 0)
        }

        //검색 버튼 클릭시 검색 이벤트 실행
        searchButton.setOnClickListener {
            inputAndSearch()
        }
        //키보드 엔터 클릭시 검색 이벤트 실행
        bookSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                inputAndSearch()
                return@OnKeyListener true
            }
            false
        })

        //메뉴 버튼 클릭 이벤트
        MMSearch_card.setOnClickListener {
            fragChangeManager.setDataFragment(SearchFragment(), parentFragmentManager, "?rt=MM")
        }
        DDSearch_card.setOnClickListener {
            fragChangeManager.setDataFragment(SearchFragment(), parentFragmentManager, "?rt=DD")
        }
        FAQ_card.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=faq"))
            startActivity(intent)
        }
        notice_card.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=notice"))
            startActivity(intent)
        }
        award_card.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=award"))
            startActivity(intent)
        }
    }

    //입력된 값이 있는지 확인하고 있다면 검색 Fragment로 이동
    private fun inputAndSearch() {
        when {
            bookSearch.text.toString().isEmpty() ->
                makeText(context, "입력된 값이 없습니다.", LENGTH_LONG).show()
            bookSearch.text.toString() == " " ->
                makeText(context, "입력된 값이 없습니다.", LENGTH_LONG).show()
            else ->
                fragChangeManager.setDataFragment(
                    SearchFragment(),
                    parentFragmentManager,
                    "?q=" + bookSearch.text.toString()
                )
        }
    }

    //메인화면에 있는 새로 등록된 도서와 대여 도서 정보를 불러와 표시한다.
    private fun listStartUp() {
        CoroutineScope(Main).launch {
            try {
                withContext(Default) {
                    //북리스트를 불러오고 ViewModel에 저장하한다.
                    dataManager.newBookList(viewModel)
                    dataManager.learningBookListView(viewModel)
                }
                //데이터 값을 Recyclerview에 넣어준다.
                for (i in viewModel.getNewList().indices) {
                    newBookListAdapter.addItem(viewModel.getNewList()[i])
                }
                for (i in viewModel.getLearningList().indices) {
                    learningBookListAdapter.addItem(viewModel.getLearningList()[i])
                }

            } catch (e: Exception) {
                Log.e("listStartUp", "Error : $e")
            }
        }
    }
}
