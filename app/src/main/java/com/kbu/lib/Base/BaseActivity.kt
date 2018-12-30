package com.kbu.lib.Base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import com.kbu.lib.R
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.graphics.Color.parseColor
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.os.Build
import android.graphics.Color.parseColor
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR









abstract class BaseActivity(val LayoutID : Int) : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(LayoutID)
        set()
    }
    fun check(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if(connectivityManager.activeNetworkInfo != null ){
//
//        }
    }
    abstract fun set()

}