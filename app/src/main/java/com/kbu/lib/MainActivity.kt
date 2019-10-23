package com.kbu.lib

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.Recycler.MainNoticeRecycler
import com.kbu.lib.data.Mainbook
import com.kbu.lib.data.Mainnotice
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun setEvent() {
        Booksearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                when {
                    Booksearch.text.toString().isEmpty() -> Toast.makeText(
                        this,
                        "입력된 값이 없습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                    Booksearch.text.toString() == " " -> Toast.makeText(
                        this,
                        "입력된 값이 없습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> {
                        val intent = Intent(this, BookSearchActivity::class.java)
                        intent.putExtra("name", Booksearch.text.toString())
                        startActivity(intent)
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

        Bsearch_button.setOnClickListener {
            when {
                Booksearch.text.toString().isEmpty() -> Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                Booksearch.text.toString() == " " -> Toast.makeText(this, "입력된 값이 없습니다.", Toast.LENGTH_LONG).show()
                else -> {
                    val intent = Intent(this, BookSearchActivity::class.java)
                    intent.putExtra("name", Booksearch.text.toString())
                    startActivity(intent)
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
                val newBookList = arrayListOf<Mainbook>()
                withContext(Dispatchers.IO) {
                    try {
                        val elements =
                            Jsoup.connect("$libURL/Search/New").get().select("div[class=guideBox]")
                                .select("ul[class=sponge-newbook-list]").select("li")

                        for (i in elements.indices)
                            newBookList.add(
                                Mainbook(
                                    elements[i].select("li a img").attr("src").toString(),
                                    elements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                        Log.e("connect error", e.toString())
                    }
                }
                newbook.adapter = MainBooks(newBookList)
            }
            GlobalScope.launch(Dispatchers.Main) {
                val bookRentalList = arrayListOf<Mainbook>()
                withContext(Dispatchers.IO) {
                    try {
                        val elements =
                            Jsoup.connect("$libURL/Search/").get()
                                .select("div[class=col-md-6 bostbooklist]")
                                .select("ul[class=user-welcome-page-thumb]").select("li")
                        for (i in elements.indices)
                            bookRentalList.add(
                                Mainbook(
                                    elements[i].select("a img").attr("src").toString(),
                                    elements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                        Log.e("connect error", e.toString())
                    }
                }
                bookrental1.adapter = MainBooks(bookRentalList)
            }
            GlobalScope.launch(Dispatchers.Main) {
                val noticeList = arrayListOf<Mainnotice>()

                withContext(Dispatchers.IO) {
                    try {
                        val elements =
                            Jsoup.connect("$libURL/").get().select("div[class=tab-pane active]")
                                .select("ul[class=data]")
                                .select("li[class=list]")
                        for (i in elements.indices)
                            noticeList.add(
                                Mainnotice(
                                    elements[i].select("a").text().toString(),
                                    elements[i].select("p").text().toString(),
                                    elements[i].select("a").attr("href").toString()
                                )
                            )
                    } catch (e: Exception) {
                        Log.e("connect error", e.toString())
                    }
                }
                notice_main.adapter = MainNoticeRecycler(noticeList)
            }
        }
        catch (e: Exception) {
            AlertDialog.Builder(this)
                .setTitle("인터넷이 연결되어있지않습니다.")
                .setMessage("인터넷이 연결되어 있지 않거나 연결에 문제가 발생하였습니다.\n나중에 다시 시도해주세요")
                .setPositiveButton(
                    "종료",
                    DialogInterface.OnClickListener(function = positiveButtonClick)
                )
                .show()
            Log.e("connect error", e.toString())
        }
        setEvent()
    }
}



