package com.kbu.lib

import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.BookSearchRecycler
import com.kbu.lib.data.Search
import kotlinx.android.synthetic.main.activity_book_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class BookSearchActivity : BaseActivity(R.layout.activity_book_search) {
    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.groupId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    override fun settingActivity() {
        setEvent()
        var listCount = 1

        Toast.makeText(this, "\"" + intent.getStringExtra("name") + "\" 책을 검색합니다.", Toast.LENGTH_SHORT).show()
        val url: String = intent.getStringExtra("name")
        val libURL = "http://lib.bible.ac.kr/Search/?q=$url&searchTruncate=true&campuscode=00"
        val arrayList = arrayListOf<Search>()

        Search_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        Search_recyclerview.setHasFixedSize(true)

        GlobalScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                //Log.d("Test",libURL)
                val elements =
                    Jsoup.connect(libURL).get().select("div[class=col-md-9 page-search-left-list]")
                        .select("div[class=search-list-result]")

                for (i in elements.indices) {
                    arrayList.add(
                        Search(
                            elements[i].select("a")[0].select("img").attr("src").toString(),
                            elements[i].select("div[class=sponge-list-title]").select("a").text().toString(),
                            elements[i].select("div[class=sponge-list-content]").select("span")[0].text().toString() + elements[i].select(
                                "div[class=sponge-list-content]"
                            ).select("span")[1].text().toString(),
                            elements[i].select("div[class=sponge-list-title]").select("a").attr("href").toString()
                        )
                    )
                }
            }
            Search_recyclerview.adapter = BookSearchRecycler(arrayList)

        }
        search_cardview.setOnClickListener {
            listCount++
            GlobalScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    val elements = Jsoup.connect("$libURL&p=$listCount").get()
                        .select("div[class=col-md-9 page-search-left-list]")
                        .select("div[class=search-list-result]")
                    for (i in elements.indices) {
                        arrayList.add(
                            Search(
                                elements[i].select("a")[0].select("img").attr("src").toString(),
                                elements[i].select("div[class=sponge-list-title]").select("a").text().toString(),
                                elements[i].select("div[class=sponge-list-content]").select("span")[0].text().toString() + elements[i].select(
                                    "div[class=sponge-list-content]"
                                ).select("span")[1].text().toString(),
                                elements[i].select("div[class=sponge-list-title]").select("a").attr(
                                    "href"
                                ).toString()
                            )
                        )
                    }
                }
                Search_recyclerview.adapter = BookSearchRecycler(arrayList)
                Search_recyclerview.smoothScrollToPosition(arrayList.size - 11)
            }
        }
    }
}
