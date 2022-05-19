package com.kbu.lib.function

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import com.kbu.lib.base.BaseDataFunction
import com.kbu.lib.data.recyclerData.SearchList
import com.kbu.lib.ui.search.SearchRecycler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFunction : BaseDataFunction(){
    //책 검색 리스트를 불러온다.
    private fun bookListIndex(url: String): ArrayList<SearchList> {
        val arrayList = arrayListOf<SearchList>()
        val elements =
            dataParser.elements(
                "$libURL/Search/$url",
                "div[class=col-md-9 page-search-left-list]",
                "div[class=search-list-result]"
            )

        for (i in elements.indices) {
            arrayList.add(
                SearchList(
                    elements[i].select("a")[0].select("img").attr("src").toString(),
                    elements[i].select("div[class=sponge-list-title]").select("a").text().toString(),
                    elements[i].select("div[class=sponge-list-content]").select("span")[0].text().toString() + elements[i].select("div[class=sponge-list-content]").select("span")[1].text().toString(),
                    elements[i].select("div[class=sponge-list-title]").select("a")
                        .attr("href").toString()
                )
            )
        }
        return arrayList
    }

    //책 리스트를 불러와 사용자에게 표시해준다.
    fun listIndex(URL : String, searchAdapter: SearchRecycler, Context : Context) {
        CoroutineScope(Dispatchers.Main).launch {
            var arrayList = arrayListOf<SearchList>()
            try {
                withContext(Dispatchers.Default) {
                    arrayList = bookListIndex(URL)
                }
            } catch (e: Exception) {
                Log.e("BookList", "Error : $e")
            }
            //리스트 항목을 확인하고 없다면 메세지 출력
            if (1 <= arrayList.size)
                for (i in arrayList.indices)
                    searchAdapter.addItem(arrayList[i])
            else
                makeText(Context, "항목이 없습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    fun listUp(URL: String, searchAdapter: SearchRecycler,context: Context){
        CoroutineScope(Dispatchers.Default).launch {
            try {
                //데이터를 불러오고 변수에 저장한다.
                val arrayListNew = bookListIndex(
                    URL + "&p=" + searchAdapter.getCount()
                )
                withContext(Dispatchers.Main) {
                    //데이터를 Recyclerview에 넣어준다.
                    for (i in arrayListNew.indices) {
                        searchAdapter.addItem(arrayListNew[i])
                    }
                    makeText(context, "새로운 정보를 불러왔습니다.", Toast.LENGTH_SHORT).show()
                    searchAdapter.setCount() //북리스트르 페이지를 +1를 하여 다음 페이지 위치 지정
                }
            } catch (e: Exception) {
                Log.e("newBookList", "Error : $e")
                withContext(Dispatchers.Main) {
                   makeText(context, "정보에 문제가 생겨 불러오기를 중단했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}