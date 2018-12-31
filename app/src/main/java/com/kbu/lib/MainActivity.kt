package com.kbu.lib

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.Recycler.MainNotice_Recycler
import com.kbu.lib.data.Mainbook
import com.kbu.lib.data.Mainnotice
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : BaseActivity(R.layout.activity_main) {

    @SuppressLint("ShowToast")
    fun onevent(){
        LinearBackgroud.setOnClickListener {
            val imm  = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(Booksearch.windowToken, 0)
        }
        Bsearch_button.setOnClickListener{
            if (Booksearch.text.toString().isEmpty()) {
                Toast.makeText(this,"입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,Booksearch.text.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun set() {
        val libURL = "http://lib.bible.ac.kr"
        val newbooklist = arrayListOf<Mainbook>()
        val bookrentallist = arrayListOf<Mainbook>()
        val notielist = arrayListOf <Mainnotice>()

        GlobalScope.launch(Dispatchers.Default) {
            val newbook_elements =
                Jsoup.connect("$libURL/Search/New").get().select("div[class=guideBox]").select("ul[class=sponge-newbook-list]").select("li")
            val bookrental_elements =
                Jsoup.connect("$libURL/Search/").get().select("div[class=col-md-6 bostbooklist]").select("ul[class=user-welcome-page-thumb]").select("li")
            val noticelements  =
                Jsoup.connect("$libURL/").get().select("div[class=tab-pane active]").select("ul[class=data]").select("li[class=list]")

            for (i in newbook_elements.indices)
                newbooklist.add(Mainbook(newbook_elements[i].select("li a img").attr("src").toString()))
            for (i in bookrental_elements.indices)
                bookrentallist.add(Mainbook(bookrental_elements[i].select("a img").attr("src").toString()))
            for (i in noticelements.indices)
               notielist.add(Mainnotice(
                   noticelements[i].select("a").text().toString(),
                   noticelements[i].select("p").text().toString()))

            GlobalScope.launch(Dispatchers.Main) {
                newbook.adapter = MainBooks(newbooklist)
                bookrental1.adapter = MainBooks(bookrentallist)
                notice_main.adapter = MainNotice_Recycler(notielist)
            }
        }
        newbook.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        bookrental1.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        notice_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        newbook.setHasFixedSize(true)
        bookrental1.setHasFixedSize(true)
        notice_main.setHasFixedSize(true)

        onevent()
    }
}


