package com.kbu.lib

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.data.Mainbook
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
        val newbooklist = arrayListOf<Mainbook>()
        val bookrentallist = arrayListOf<Mainbook>()

        GlobalScope.launch(Dispatchers.Default) {
            val newbookdocument = Jsoup.connect("http://lib.bible.ac.kr/Search/New").get()
            val bookrentaldocument = Jsoup.connect("http://lib.bible.ac.kr/Search/").get()
            val newbookelements = newbookdocument.select("div[class=guideBox]").select("ul[class=sponge-newbook-list]").select("li")
            val bookrentalelements = bookrentaldocument.select("div[class=col-md-6 bostbooklist]").select("ul[class=user-welcome-page-thumb]").select("li")

            for (i in newbookelements.indices)
                newbooklist.add(Mainbook(newbookelements[i].select("li a img").attr("src").toString()))
            for (i in bookrentalelements.indices)
                bookrentallist.add(Mainbook(bookrentalelements[i].select("a img").attr("src").toString()))
            GlobalScope.launch(Dispatchers.Main) {
                newbook.adapter = MainBooks(newbooklist)
                bookrental1.adapter = MainBooks(bookrentallist)
            }
        }
        newbook.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        bookrental1.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        newbook.setHasFixedSize(true)
        bookrental1.setHasFixedSize(true)

        onevent()
    }
}


