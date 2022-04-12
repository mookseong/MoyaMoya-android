package com.kbu.lib.function


import com.kbu.lib.data.MainViewBookList
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class DataParser {

    //elements를 데이터 양에 따라서 사용 가능하도록 정의
    fun elements(URL: String, cssQueue: String, queue_ul: String, queue_li: String): Elements =
        Jsoup.connect(URL).get().select(cssQueue).select(queue_ul).select(queue_li)

    fun elements(URL: String, cssQueue: String, queue_ul: String): Elements =
        Jsoup.connect(URL).get().select(cssQueue).select(queue_ul)

    fun elements(URL: String, cssQueue: String): Elements =
        Jsoup.connect(URL).get().select(cssQueue)

    fun elementSelect(
        element: Elements,
        ListIndex: Int,
        selectIndex: Int,
        cssQueue: String
    ): String =
        element[ListIndex].select(cssQueue)[selectIndex].text()


    fun elementsIndex(
        elements: Elements,
        queue1: String,
        queue1_key: String,
        queue2: String,
        queue2_key: String
    ): ArrayList<MainViewBookList> {
        val newList = arrayListOf<MainViewBookList>()
        for (i in elements.indices)
            newList.add(
                MainViewBookList(
                    elements[i].select(queue1).attr(queue1_key).toString(),
                    elements[i].select(queue2).attr(queue2_key).toString()
                )
            )
        return newList
    }


}