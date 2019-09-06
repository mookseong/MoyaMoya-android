package com.kbu.lib

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import android.support.v7.app.AlertDialog
import android.util.Log

class MainActivity : BaseActivity(R.layout.activity_main) {

    fun mainSplash() {
        val hd = Handler()
    }

    override fun setEvent() {
        Booksearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                when {
                    Booksearch.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "입력된 값이 없습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                    Booksearch.text.toString() == " " -> Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                    else -> {
                        val BookSearch = Intent(this, BookSearchActivity::class.java)
                        BookSearch.putExtra("name", Booksearch.text.toString())
                        startActivity(BookSearch)
                    }
                }
                return@OnKeyListener true
            }
            false
        })
        LinearBackgroud.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(Booksearch.windowToken, 0)
        }
//        developer_pay.setOnClickListener{
//            Toast.makeText(this,"준비중입니다.", Toast.LENGTH_LONG).show()
////            val Developer_money = Intent(this, DeveloperINFOActivity::class.java)
////            startActivity(Developer_money)
//        }
        Bsearch_button.setOnClickListener {
            when {
                Booksearch.text.toString().isEmpty() -> Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                Booksearch.text.toString() == " " -> Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                else -> {
                    val BookSearch = Intent(this, BookSearchActivity::class.java)
                    BookSearch.putExtra("name", Booksearch.text.toString())
                    startActivity(BookSearch)
                }
            }
        }
    }

    override fun settingActivity() {
        newbook.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        newbook.setHasFixedSize(true)
        bookrental1.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        bookrental1.setHasFixedSize(true)
        notice_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        notice_main.setHasFixedSize(true)

        try {
            GlobalScope.launch(Dispatchers.Main) {
                val newbooklist = arrayListOf<Mainbook>()
                val asysnc = GlobalScope.async(Dispatchers.IO) {
                    try {
                        val newbook_elements =
                            Jsoup.connect("$libURL/Search/New").get().select("div[class=guideBox]")
                                .select("ul[class=sponge-newbook-list]").select("li")

                        for (i in newbook_elements.indices)
                            newbooklist.add(
                                Mainbook(
                                    newbook_elements[i].select("li a img").attr("src").toString(),
                                    newbook_elements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                       Log.e("connect error", e.toString())
                    }
                }.await()
                newbook.adapter = MainBooks(newbooklist)
            }
            GlobalScope.launch(Dispatchers.Main) {
                val bookrentallist = arrayListOf<Mainbook>()
                val asysnc = GlobalScope.async(Dispatchers.IO) {
                    try {
                        val bookrental_elements =
                            Jsoup.connect("$libURL/Search/").get().select("div[class=col-md-6 bostbooklist]")
                                .select("ul[class=user-welcome-page-thumb]").select("li")
                        for (i in bookrental_elements.indices)
                            bookrentallist.add(
                                Mainbook(
                                    bookrental_elements[i].select("a img").attr("src").toString(),
                                    bookrental_elements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                        Log.e("connect error", e.toString())
                    }
                }.await()
                bookrental1.adapter = MainBooks(bookrentallist)
            }
            GlobalScope.launch(Dispatchers.Main) {
                val notielist = arrayListOf<Mainnotice>()

                val asysnc = GlobalScope.async(Dispatchers.IO) {
                    try {
                        val noticelements =
                            Jsoup.connect("$libURL/").get().select("div[class=tab-pane active]")
                                .select("ul[class=data]")
                                .select("li[class=list]")
                        for (i in noticelements.indices)
                            notielist.add(
                                Mainnotice(
                                    noticelements[i].select("a").text().toString(),
                                    noticelements[i].select("p").text().toString(),
                                    noticelements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                        Log.e("connect error", e.toString())
                    }
                }.await()
                notice_main.adapter = MainNotice_Recycler(notielist)
            }
        }
        catch (e: Exception) {
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("오류가 발생했습니다.")
                setMessage("알 수 없는 오류가 발생했습니다. 다시 시도해주시기 바랍니다.")
                setPositiveButton("종료", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
            Log.e("connect error", e.toString())
        }
        setEvent()
    }
}



