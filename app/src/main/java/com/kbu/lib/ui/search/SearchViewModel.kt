package com.kbu.lib.ui.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.kbu.lib.data.SearchList


class SearchViewModel : ViewModel() {

    private var bookTitle: String = ""
    private var listCount: Int = 2
    private lateinit var adapter: SearchRecycler

    fun setListCount()  {
        listCount += 1
    }

    fun oneListCount(){
        listCount = 2
    }

    fun getListCount(): Int {
        return listCount
    }

    fun setAdapter(searchRecycler: SearchRecycler) {
        adapter = searchRecycler
    }

    fun getAdapter() = adapter

    fun setBookTitle(book: String) {
        bookTitle = book
    }

    fun getBookTitle(): String = bookTitle
}
