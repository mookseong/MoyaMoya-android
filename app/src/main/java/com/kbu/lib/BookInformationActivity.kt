package com.kbu.lib

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kbu.lib.Base.BaseActivity
import com.kbu.lib.Recycler.BookInformationRecycler
import com.kbu.lib.data.Information
import kotlinx.android.synthetic.main.activity_book_information.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class BookInformationActivity : BaseActivity(R.layout.activity_book_information) {
    override fun setEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun settingActivity() {
        val url: String = intent.getStringExtra("URL")

        val possessInformation = arrayListOf<Information>()

        possession_Information.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        possession_Information.setHasFixedSize(true)
        possession_Information.adapter = BookInformationRecycler(possessInformation)


            val arrayList = arrayListOf<String>()
            val arrayListID = arrayListOf<TextView>(
                Title_information,
                DCC_information,
                number_information,
                weiter_information,
                text_information
            )

            val list = arrayListOf("DDC", "청구기호", "서명/저자", "발행사항")
        GlobalScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.IO) {
                val elementTitle =
                    Jsoup.connect(libURL + url).get()
                        .select("div[class=col-md-10 detail-table-right]")
                val elements = elementTitle.select("dl")
                arrayList.add(elementTitle.select("div[class=sponge-book-title]").text().toString())
                withContext(Dispatchers.Default) {
                    for (i in elements.indices) {
                        for (j in 0..3) {
                            if (elements[i].select("dt").text().toString() == list[j]) {
                                arrayList.add(elements[i].select("dd").text().toString())
                            }
                        }
                    }
                }
            }
            for (i in arrayList.indices)
                arrayListID[i].text = arrayList[i]
        }
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val possessInformationElements =
                    Jsoup.connect(libURL + url).get()
                        .select("div[class=sponge-guide-Box-table sponge-detail-table]")
                        .select("table[class=table-striped sponge-table-default]")
                        .select("tbody tr")
                withContext(Dispatchers.Default) {
                    for (i in possessInformationElements.indices) {
                        possessInformation.add(
                            Information(
                                possessInformationElements[i].select("td")[0].text().toString(),
                                possessInformationElements[i].select("td")[1].text().toString(),
                                possessInformationElements[i].select("td")[2].text().toString(),
                                possessInformationElements[i].select("td")[3].text().toString()
                            )
                        )
                    }
                }
            }
            (possession_Information.adapter as BookInformationRecycler).notifyDataSetChanged()
        }
        Glide.with(this).load(intent.getStringExtra("IMG")).into(Img_information)
    }
}
