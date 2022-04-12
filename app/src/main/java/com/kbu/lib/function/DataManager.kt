package com.kbu.lib.function

import com.kbu.lib.data.Information
import com.kbu.lib.data.SearchList
import com.kbu.lib.ui.main.MainViewModel


//데이터 파씽을 함수로 정리해서 관리
class DataManager {

    private val libURL = "http://lib.bible.ac.kr"
    private val fragChangeManager = FragmentChangeManager()
    private val dataParser = DataParser()

    //책 검색 리스트를 불러온다.
    fun bookListIndex(url: String?): ArrayList<SearchList> {
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

    //도서 정보를 불러온다.
    fun bookInfo(url: String?, bookInfoList: ArrayList<String>): ArrayList<String> {
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
    fun bookISBN(url: String?) : String{
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
    fun bookBorrow(url: String?): ArrayList<Information> {
        val arrayList = arrayListOf<Information>()
        val borrowElements =
            dataParser.elements(
                url.toString(), "div[class=sponge-guide-Box-table sponge-detail-table]",
                "table[class=table-striped sponge-table-default]",
                "tbody tr"
            )
        for (i in borrowElements.indices) {
            arrayList.add(
                Information(
                    dataParser.elementSelect(borrowElements, i, 0, "td"),
                    dataParser.elementSelect(borrowElements, i, 1, "td"),
                    dataParser.elementSelect(borrowElements, i, 2, "td"),
                    dataParser.elementSelect(borrowElements, i, 3, "td")
                )
            )
        }
        return arrayList
    }

    //최근대여 목록을 불러온다
    fun learningBookListView(viewModel: MainViewModel) {
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
    fun newBookList(viewModel: MainViewModel) {
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


}