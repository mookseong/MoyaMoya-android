package com.kbu.lib.ui.main


import androidx.lifecycle.ViewModel
import com.kbu.lib.data.MainViewBookList

class MainViewModel : ViewModel() {
    private lateinit var newBookList: ArrayList<MainViewBookList>
    private lateinit var learningBookList: ArrayList<MainViewBookList>

    private lateinit var newBookListAdapter: BooksMainRecycler
    private lateinit var learningBookListAdapter: BooksMainRecycler

    fun setNewBookListAdapter(booksMainRecycler: BooksMainRecycler) {
        newBookListAdapter = booksMainRecycler
    }

    fun getNewBookListAdapter(): BooksMainRecycler = newBookListAdapter

    fun setLearningBookListAdapter(booksMainRecycler: BooksMainRecycler) {
        newBookListAdapter = booksMainRecycler
    }

    fun getLearningBookListAdapter(): BooksMainRecycler = learningBookListAdapter

    fun setNewList(List: ArrayList<MainViewBookList>) {
        this.newBookList = List
    }

    fun getNewList(): ArrayList<MainViewBookList> = newBookList


    fun setLearningList(List: ArrayList<MainViewBookList>) {
        this.learningBookList = List
    }

    fun getLearningList(): ArrayList<MainViewBookList> = learningBookList


}