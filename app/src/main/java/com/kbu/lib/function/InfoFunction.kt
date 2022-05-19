package com.kbu.lib.function

import android.util.Log
import android.widget.TextView
import com.kbu.lib.base.BaseDataFunction
import com.kbu.lib.data.layoutData.InfoPages
import com.kbu.lib.data.recyclerData.InfoBook
import com.kbu.lib.databinding.InfoBooksFragmentBinding
import com.kbu.lib.ui.information.InfoBooksRecycler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class InfoFunction : BaseDataFunction(){
    //도서 정보를 불러온다.
    private fun bookInfo(url: String?, bookInfoList: ArrayList<String>): ArrayList<String> {
        val arrayList = arrayListOf<String>()
        val elements =
            dataParser.elements(
                url.toString(),
                "div[class=col-md-10 detail-table-right]",
                "dl"
            )
        for (i in elements.indices) {
            for (j in bookInfoList.indices) {
                if (elements[i].select("dt").text().toString() == bookInfoList[j]) {
                    arrayList.add(elements[i].select("dd").text().toString())
                }
            }
        }
        return arrayList
    }

    //도서 ISBN 정보를 불러온다.
    private fun bookISBN(url: String?) : String{
        val elements =
            dataParser.elements(
                url.toString(),
                "div[class=col-md-10 detail-table-right]",
                "dl"
            )
        for (i in elements.indices) {
            if (elements[i].select("dt").text().toString() == "ISBN") {
                return elements[i].select("dd").text().toString()
            }
        }
        return "Error"
    }


    //대여관련 정보를 불러온다.
    private fun bookBorrow(url: String?): ArrayList<InfoBook> {
        val arrayList = arrayListOf<InfoBook>()
        val borrowElements =
            dataParser.elements(
                url.toString(), "div[class=sponge-guide-Box-table sponge-detail-table]",
                "table[class=table-striped sponge-table-default]",
                "tbody tr"
            )
        for (i in borrowElements.indices) {
            arrayList.add(
                InfoBook(
                    dataParser.elementSelect(borrowElements, i, 0, "td"),
                    dataParser.elementSelect(borrowElements, i, 1, "td"),
                    dataParser.elementSelect(borrowElements, i, 2, "td"),
                    dataParser.elementSelect(borrowElements, i, 3, "td")
                )
            )
        }
        return arrayList
    }


    fun bookTitle(url: String, binding : InfoBooksFragmentBinding) {
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
                binding.infoPages = InfoPages(
                    getTitle,
                    ""
                )
            } catch (e: Exception) {
                binding.infoTitle.text = "오류가 발생했습니다."
                Log.e("InfoBookTitleError", e.toString())
            }
        }
    }

    fun bookInfo(url: String, binding : InfoBooksFragmentBinding) {
        CoroutineScope(Dispatchers.Default).launch {
            val arrayListID = arrayListOf<TextView>(
                binding.infoCallNumber,
                binding.infoPublication,
                binding.infoWriter
            )
            try {
                val arrayList =
                    bookInfo(libURL + url, arrayListOf("발행사항", "청구기호", "기타저자"))
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
                    var bookISBM = bookISBN(libURL + url)
                    if (13 < bookISBM.length)
                        bookISBM = bookISBM.substring(0 until 13)

                    bookInfoISBN = dataParser.elements(
                        "https://lib.bible.ac.kr/Naver/NaverDetail?isbn=$bookISBM",
                        "div[class=sponge_naver_detalil]",
                        "div[id=bookIntroContent]"
                    ).text()
                }
                binding.infoBookData.text = bookInfoISBN
            } catch (e: Exception) {
                Log.e("TEST", "Error : $e")
                binding.infoBookData.text = "알 수 없는 오류가 발생했습니다."
            }
        }
    }

    fun borrowList(url: String, infoBooksRecycler: InfoBooksRecycler) {
        CoroutineScope(Dispatchers.Main).launch {
            var arrayList = arrayListOf<InfoBook>()
            try {
                withContext(Dispatchers.Default) {
                    arrayList = bookBorrow(libURL + url)
                }
            } catch (e: Exception) {
                Log.e("borrowListUp", "Error : $e")
            }
            for (i in arrayList.indices) {
                infoBooksRecycler.addItem(arrayList[i])
            }
        }
    }
}