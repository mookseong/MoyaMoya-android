package com.kbu.lib.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
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
import com.kbu.lib.databinding.MainFragmentBinding
import com.kbu.lib.function.FragmentChangeManager
import com.kbu.lib.function.MainFunction
import com.kbu.lib.ui.search.SearchFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main


class MainFragment : BaseFragment<MainFragmentBinding>(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var newBookListAdapter: BooksMainRecycler
    private lateinit var learningBookListAdapter: BooksMainRecycler

    private val mainFunction = MainFunction()

    override fun initListener() {
        super.initListener()
        //버튼 클릭 이벤트
        binding.backGround.setOnClickListener {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.bookSearch.windowToken, 0)
        }

        //검색 버튼 클릭시 검색 이벤트 실행
        binding.searchButton.setOnClickListener {
            mainFunction.inputAndSearch(binding,requireContext(),parentFragmentManager)
        }

        //키보드 엔터 클릭시 검색 이벤트 실행
        binding.bookSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                mainFunction.inputAndSearch(binding,requireContext(),parentFragmentManager)
                return@OnKeyListener true
            }
            false
        })

        //메뉴 버튼 클릭 이벤트
        binding.MMSearchCard.setOnClickListener {
            FragmentChangeManager(SearchFragment(), parentFragmentManager).setInfoFragment("?rt=MM")
        }
        binding.DDSearchCard.setOnClickListener {
            FragmentChangeManager(SearchFragment(), parentFragmentManager).setInfoFragment("?rt=DD")
        }
        binding.FAQCard.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=faq")))
        }
        binding.noticeCard.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=notice")))
        }
        binding.awardCard.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.bible.ac.kr/Board?n=award")))
        }
    }

    override fun afterViewCreated() {
        super.afterViewCreated()
        //Recyclerview 어뎁터 설정
        binding.newBookList.adapter = newBookListAdapter
        binding.learningBookList.adapter = learningBookListAdapter
        binding.newBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.learningBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.newBookList.setHasFixedSize(true)
        binding.learningBookList.setHasFixedSize(true)
    }

    override fun initView() {
        super.initView()
        //데이터가 있는 확인하고 없으면 어뎁터를 초기화를 진행해준다
        if (!::newBookListAdapter.isInitialized && !::learningBookListAdapter.isInitialized) {
            newBookListAdapter =
                BooksMainRecycler(arrayListOf(), parentFragmentManager)
            learningBookListAdapter =
                BooksMainRecycler(arrayListOf(), parentFragmentManager)
            mainFunction.listStartUp(viewModel, newBookListAdapter, learningBookListAdapter)
        }
    }
}
