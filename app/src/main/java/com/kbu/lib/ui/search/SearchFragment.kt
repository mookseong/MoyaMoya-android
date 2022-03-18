package com.kbu.lib.ui.search


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.SearchList
import com.kbu.lib.function.DataParser
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.*
import java.lang.Exception

class SearchFragment : BaseFragment(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchRecycler.setHasFixedSize(true)
        bookListIndex()
    }

    private fun bookListIndex() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                var arrayList = arrayListOf<SearchList>()
                withContext(Dispatchers.Default) {
                    arrayList = dataManager.bookListIndex(arguments?.getString("title"))
                }
                searchRecycler.adapter = SearchRecycler(arrayList)
            } catch (e: Exception) {
                Log.e("newBookList", "Error : $e")
            }
        }

    }

}