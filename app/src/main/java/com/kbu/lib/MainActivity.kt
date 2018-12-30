package com.kbu.lib

import android.renderscript.Element
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.data.Mainbook
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun set() {
        val newbooklist = arrayListOf<Mainbook>()
        val bookrentallist = arrayListOf<Mainbook>()

        GlobalScope.launch(Dispatchers.Default) { // launch coroutine in the main thread
            val document = Jsoup.connect("http://lib.bible.ac.kr/Search/New").get()
            val elements = document.select("div[class=guideBox]").select("ul[class=sponge-newbook-list]").select("li")
            for (i in elements.indices){
                newbooklist.add(Mainbook(elements[i].select("li a img").attr("src").toString()))

            }
        }
        GlobalScope.launch(Dispatchers.Default) { // launch coroutine in the main thread
            val document = Jsoup.connect("http://lib.bible.ac.kr/Search/").get()
            val elements = document.select("div[class=col-md-6 bostbooklist]").select("ul[class=user-welcome-page-thumb]").select("li")
            for (i in elements.indices) {
                bookrentallist.add(Mainbook(elements[i].select("a img").attr("src").toString()))
            }
        }

        newbook.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        bookrental1.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        newbook.setHasFixedSize(true)
        bookrental1.setHasFixedSize(true)
        newbook.adapter = MainBooks(newbooklist)
        bookrental1.adapter = MainBooks(bookrentallist)

    }
}


