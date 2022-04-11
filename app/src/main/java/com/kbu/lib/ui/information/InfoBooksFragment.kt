package com.kbu.lib.ui.information


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbu.lib.R
import com.kbu.lib.base.BaseFragment
import com.kbu.lib.data.Information
import kotlinx.android.synthetic.main.info_books_fragment.*
import kotlinx.coroutines.*
import java.lang.Exception

class InfoBooksFragment : BaseFragment(R.layout.info_books_fragment) {

    companion object {
        fun newInstance() = InfoBooksFragment()
    }

    private val viewModel: InfoBooksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val infoBooksRecycler = InfoBooksRecycler(arrayListOf<Information>())
        val url: String = arguments?.getString("URL").toString()

        info_recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        info_recyclerview.setHasFixedSize(true)
        info_recyclerview.addItemDecoration(DividerItemDecoration(view.context, 1))
        info_recyclerview.adapter = infoBooksRecycler
        Glide.with(context).load(arguments?.getString("IMG").toString()).into(infoBookImg)

        if (arguments?.getString("Title") != "" && arguments?.getString("Title") != null) {
            info_title.text = arguments?.getString("Title")
            info_title_text.text = arguments?.getString("TitleText")
        } else {
            bookTitle(arguments?.getString("URL").toString())
            info_title_text.text = ""
        }


        bookInfo(url)
        borrowList(url, infoBooksRecycler)
    }

    private fun bookTitle(url: String) {
        CoroutineScope(Dispatchers.Main).launch {
            var getTitle: String
            withContext(Dispatchers.IO) {
                getTitle = dataParser.elements(
                    libURL + url,
                    "div[class=col-md-10 detail-table-right]",
                    "div[class=sponge-book-title]"
                ).text().toString()
            }
            try {
                info_title.text = getTitle
            } catch (e: Exception) {
                info_title.text = "오류가 발생했습니다."
                Log.e("InfoBookTitleError", e.toString())
            }

        }

    }

    private fun borrowList(url: String, infoBooksRecycler: InfoBooksRecycler) {
        CoroutineScope(Dispatchers.Main).launch {
            var arrayList = arrayListOf<Information>()
            try {
                withContext(Dispatchers.Default) {
                    arrayList = dataManager.bookBorrow(libURL + url)
                }
            } catch (e: Exception) {
                Log.e("borrowListUp", "Error : $e")
            }
            for (i in arrayList.indices) {
                infoBooksRecycler.addItem(arrayList[i])
            }
        }
    }

    private fun bookInfo(url: String) {
        CoroutineScope(Dispatchers.Default).launch {

            val arrayListID = arrayListOf<TextView>(
                info_call_number,
                info_publication,
                info_writer
            )
            try {
                val arrayList =  dataManager.bookInfo(libURL + url, arrayListOf("발행사항", "청구기호", "기타저자"))
                withContext(Dispatchers.Main) {
                    for (i in arrayListID.indices)
                        arrayListID[i].text = arrayList[i]
                }

            } catch (e: Exception) {
                Log.e("bookInfoUp", "Error : $e")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            var bookInfoISBN: String
            try {
                withContext(Dispatchers.Default) {
                    var bookISBM = dataManager.bookISBN(libURL + url)
                    if (13 < bookISBM.length)
                        bookISBM = bookISBM.substring(0 until 13)

                    bookInfoISBN = dataParser.elements(
                        "https://lib.bible.ac.kr/Naver/NaverDetail?isbn=$bookISBM",
                        "div[class=sponge_naver_detalil]",
                        "div[id=bookIntroContent]"
                    ).text()
                }
                info_book_data.text = bookInfoISBN
            } catch (e: Exception) {
                Log.e("TEST", "Error : $e")
                info_book_data.text = "알 수 없는 오류가 발생했습니다."
            }
        }


    }
}