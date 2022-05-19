package com.kbu.lib.function


import com.kbu.lib.base.BaseDataFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoBooksParser : BaseDataFunction() {

     fun bookTitle(url: String): String {
        var getTitle  = ""
        CoroutineScope(Dispatchers.IO).launch {
            getTitle = dataParser.elements(
                libURL + url,
                "div[class=col-md-10 detail-table-right]",
                "div[class=sponge-book-title]"
            ).text().toString()
        }
        return getTitle
    }



}