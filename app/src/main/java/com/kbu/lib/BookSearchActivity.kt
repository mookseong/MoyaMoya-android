package com.kbu.lib

import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.BookSearch_Recycler
import com.kbu.lib.Recycler.MainBooks
import com.kbu.lib.data.Search
import kotlinx.android.synthetic.main.activity_book_information.*
import kotlinx.android.synthetic.main.activity_book_search.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.lang.Thread.sleep

class BookSearchActivity : BaseActivity(R.layout.activity_book_search) {
    fun onevent(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.groupId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun set(){
        onevent()
        var listCount = 1

        Toast.makeText(this,"\""+ intent.getStringExtra("name") + "\" 책을 검색합니다.", Toast.LENGTH_SHORT).show()
        val URL : String = intent.getStringExtra("name")
        val libURL = "http://lib.bible.ac.kr/Search/?q=$URL&searchTruncate=true&campuscode=00"
        val Datalist = arrayListOf<Search>()

        Search_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        Search_recyclerview.setHasFixedSize(true)

        GlobalScope.launch(Dispatchers.Main) {

            val asysnc = GlobalScope.async(Dispatchers.IO) {
                //Log.d("Test",libURL)
                val DATA_elementstitle_P1 = Jsoup.connect(libURL).get().select("div[class=col-md-9 page-search-left-list]").select("div[class=search-list-result]")

                for (i in DATA_elementstitle_P1.indices) {
                    Datalist.add(Search(
                        DATA_elementstitle_P1[i].select("a")[0].select("img").attr("src").toString(),
                        DATA_elementstitle_P1[i].select("div[class=sponge-list-title]").select("a").text().toString(),
                        DATA_elementstitle_P1[i].select("div[class=sponge-list-content]").select("span")[0].text().toString() + DATA_elementstitle_P1[i].select("div[class=sponge-list-content]").select("span")[1].text().toString(),
                        DATA_elementstitle_P1[i].select("div[class=sponge-list-title]").select("a").attr("href").toString()
                    ))
                }
            }.await()
            Search_recyclerview.adapter = BookSearch_Recycler(Datalist)

        }
        search_cardview.setOnClickListener{
            listCount ++
            GlobalScope.launch(Dispatchers.Main) {
                val asysnc = GlobalScope.async(Dispatchers.IO) {
                    val DATA_elementstitle_P1 = Jsoup.connect("$libURL&p=$listCount").get().select("div[class=col-md-9 page-search-left-list]").select("div[class=search-list-result]")
                    for (i in DATA_elementstitle_P1.indices) {
                        Datalist.add(Search(
                            DATA_elementstitle_P1[i].select("a")[0].select("img").attr("src").toString(),
                            DATA_elementstitle_P1[i].select("div[class=sponge-list-title]").select("a").text().toString(),
                            DATA_elementstitle_P1[i].select("div[class=sponge-list-content]").select("span")[0].text().toString() + DATA_elementstitle_P1[i].select("div[class=sponge-list-content]").select("span")[1].text().toString(),
                            DATA_elementstitle_P1[i].select("div[class=sponge-list-title]").select("a").attr("href").toString()
                        ))
                    }
                }.await()
                Search_recyclerview.adapter = BookSearch_Recycler(Datalist)
                Search_recyclerview.smoothScrollToPosition (Datalist.size - 11)

            }
        }
    }
}
