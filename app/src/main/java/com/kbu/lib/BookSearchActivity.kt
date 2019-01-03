package com.kbu.lib

import android.widget.Toast
import com.kbu.lib.Base.BaseActivity

class BookSearchActivity : BaseActivity(R.layout.activity_book_search) {

    override fun set(){
        Toast.makeText(this,intent.getStringExtra("name") + "책을 검색합니다.", Toast.LENGTH_LONG).show()
    }
}
