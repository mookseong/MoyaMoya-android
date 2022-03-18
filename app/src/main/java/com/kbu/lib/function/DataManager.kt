package com.kbu.lib.function

import com.kbu.lib.data.SearchList
import com.kbu.lib.ui.main.MainViewModel

class DataManager {

    private val libURL = "http://lib.bible.ac.kr"
    private val fragChangeManager = FragmentChangeManager()
    private val dataParser = DataParser()

    fun bookListIndex(url: String?): ArrayList<SearchList> {

        val arrayList = arrayListOf<SearchList>()

        val elements =
            dataParser.elements(
                "$libURL/Search/?q=$url",
                "div[class=col-md-9 page-search-left-list]",
                "div[class=search-list-result]"
            )
        for (i in elements.indices) {
            arrayList.add(
                SearchList(
                    elements[i].select("a")[0].select("img").attr("src").toString(),
                    elements[i].select("div[class=sponge-list-title]").select("a").text()
                        .toString(),
                    elements[i].select("div[class=sponge-list-content]")
                        .select("span")[0].text().toString()
                            + elements[i].select("div[class=sponge-list-content]")
                        .select("span")[1].text().toString(),
                    elements[i].select("div[class=sponge-list-title]").select("a")
                        .attr("href").toString()
                )
            )
        }
        return arrayList
    }

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