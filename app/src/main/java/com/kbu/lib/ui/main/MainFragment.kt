package com.kbu.lib.ui.main

import android.content.Context.INPUT_METHOD_SERVICE
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
import com.kbu.lib.function.DataManager
import com.kbu.lib.ui.search.SearchFragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class MainFragment : BaseFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        learningBookList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        newBookList.setHasFixedSize(true)
        learningBookList.setHasFixedSize(true)

        listStartUp()

        backGround.setOnClickListener {
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(bookSearch.windowToken, 0)
        }
        searchButton.setOnClickListener {
            inputAndSearch()
        }

        bookSearch.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                inputAndSearch()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun inputAndSearch() {
        when {
            bookSearch.text.toString().isEmpty() ->
                makeText(context, "입력된 값이 없습니다.", LENGTH_LONG).show()
            bookSearch.text.toString() == " " ->
                makeText(context, "입력된 값이 없습니다.", LENGTH_LONG).show()
            else ->
                fragChangeManager.setDataAtFragment(
                    SearchFragment(),
                    parentFragmentManager,
                    bookSearch.text.toString()
                )
        }
    }


    private fun listStartUp() {
        CoroutineScope(Main).launch {
            try {
                withContext(Default) {
                    dataManager.newBookList(viewModel)
                    dataManager.learningBookListView(viewModel)
                }
                newBookList.adapter = BooksMainRecycler(viewModel.getNewList())
                learningBookList.adapter = BooksMainRecycler(viewModel.getLearningList())
            } catch (e: Exception) {
                Log.e("listStartUp", "Error : $e")
            }
        }
    }


}
