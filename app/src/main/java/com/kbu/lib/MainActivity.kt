package com.kbu.lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.Recycler.MainNotice_Recycler
import com.kbu.lib.data.Mainbook
import com.kbu.lib.data.Mainnotice
import com.kbu.lib.data.Search
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import android.view.inputmethod.EditorInfo
import android.widget.TextView



class MainActivity : BaseActivity(R.layout.activity_main) {

    @SuppressLint("ShowToast")
    fun onevent(){

        Booksearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (Booksearch.text.toString().isEmpty())
                    Toast.makeText(this,"입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                else if (Booksearch.text.toString() == " ")
                    Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                else{
                    val BookSearch = Intent(this, BookSearchActivity::class.java)
                    BookSearch.putExtra("name", Booksearch.text.toString())
                    startActivity(BookSearch)
                }
                return@OnKeyListener true
            }
            false
        })


        LinearBackgroud.setOnClickListener {
            val imm  = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(Booksearch.windowToken, 0)
        }
        Bsearch_button.setOnClickListener{
            if (Booksearch.text.toString().isEmpty())
                Toast.makeText(this,"입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
            else if (Booksearch.text.toString() == " ")
                Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
            else{
                val BookSearch = Intent(this, BookSearchActivity::class.java)
                BookSearch.putExtra("name", Booksearch.text.toString())
                startActivity(BookSearch)
            }
        }
    }

    override fun set() {
        val libURL = "http://lib.bible.ac.kr"

        newbook.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        newbook.setHasFixedSize(true)
        bookrental1.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        bookrental1.setHasFixedSize(true)
        notice_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        notice_main.setHasFixedSize(true)

        GlobalScope.launch(Dispatchers.Main) {
            val newbooklist = arrayListOf<Mainbook>()
            val asysnc =GlobalScope.async(Dispatchers.IO) {
                val newbook_elements =
                    Jsoup.connect("$libURL/Search/New").get().select("div[class=guideBox]").select("ul[class=sponge-newbook-list]").select("li")

                for (i in newbook_elements.indices)
                    newbooklist.add(Mainbook(newbook_elements[i].select("li a img").attr("src").toString(), newbook_elements[i].select("a").attr("href").toString()))
            }.await()
            newbook.adapter = MainBooks(newbooklist)
        }

        GlobalScope.launch(Dispatchers.Main) {
            val bookrentallist = arrayListOf<Mainbook>()
            val asysnc = GlobalScope.async(Dispatchers.IO) {
                val bookrental_elements =
                    Jsoup.connect("$libURL/Search/").get().select("div[class=col-md-6 bostbooklist]").select("ul[class=user-welcome-page-thumb]").select("li")

                for (i in bookrental_elements.indices)
                    bookrentallist.add(Mainbook(bookrental_elements[i].select("a img").attr("src").toString(), bookrental_elements[i].select("a").attr("href").toString()))
            }.await()
            bookrental1.adapter = MainBooks(bookrentallist)
        }

        GlobalScope.launch(Dispatchers.Main) {
            val notielist = arrayListOf <Mainnotice>()

            val asysnc = GlobalScope.async(Dispatchers.IO) {
            val noticelements  =
                Jsoup.connect("$libURL/").get().select("div[class=tab-pane active]").select("ul[class=data]").select("li[class=list]")
            for (i in noticelements.indices)
                notielist.add(Mainnotice(
                    noticelements[i].select("a").text().toString(),
                    noticelements[i].select("p").text().toString()))
            }.await()
            notice_main.adapter = MainNotice_Recycler(notielist)
        }
        onevent()
    }
}


