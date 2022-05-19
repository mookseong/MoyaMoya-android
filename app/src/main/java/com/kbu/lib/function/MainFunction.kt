package com.kbu.lib.function

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.kbu.lib.base.BaseDataFunction
import com.kbu.lib.databinding.MainFragmentBinding
import com.kbu.lib.ui.main.BooksMainRecycler
import com.kbu.lib.ui.main.MainViewModel
import com.kbu.lib.ui.search.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFunction : BaseDataFunction() {
    //최근대여 목록을 불러온다
    private fun learningBookListView(viewModel: MainViewModel) {
        val elements =
            dataParser.elements(
                "$libURL/Search/",
                "div[class=col-md-6 bostbooklist]",
                "ul[class=user-welcome-page-thumb]",
                "li"
            )
        viewModel.setLearningList(
            dataParser.elementsIndex(
                elements,
                "a img",
                "src",
                "a",
                "href"
            )
        )
    }

    //신작 도서 목록을 불러온다.
    private fun newBookList(viewModel: MainViewModel) {
        val elements =
            dataParser.elements(
                "$libURL/Search/New",
                "div[class=guideBox]",
                "ul[class=sponge-newbook-list]",
                "li"
            )
        viewModel.setNewList(
            dataParser.elementsIndex(
                elements,
                "li a img",
                "src",
                "a",
                "href"
            )
        )
    }

    //메인화면에 있는 새로 등록된 도서와 대여 도서 정보를 불러와 표시한다.
    fun listStartUp(
        viewModel: MainViewModel,
        newBookListAdapter: BooksMainRecycler,
        learningBookListAdapter: BooksMainRecycler
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.Default) {
                    //북리스트를 불러오고 ViewModel 저장하한다.
                    newBookList(viewModel)
                    learningBookListView(viewModel)
                }
                //데이터 값을 Recyclerview 넣어준다.
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

    //입력된 값이 있는지 확인하고 있다면 검색 Fragment 이동
    fun inputAndSearch(
        binding: MainFragmentBinding,
        context: Context,
        parentFragment: FragmentManager
    ) {
        when {
            binding.bookSearch.text.toString().isEmpty() ->
                Toast.makeText(context, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
            binding.bookSearch.text.toString() == " " ->
                Toast.makeText(context, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
            else ->
                FragmentChangeManager(SearchFragment(), parentFragment).setInfoFragment("?q=" + binding.bookSearch.text.toString())
        }
    }

}