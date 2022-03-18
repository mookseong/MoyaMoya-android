package com.kbu.lib


import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.kbu.lib.ui.main.MainFragment
import kotlinx.coroutines.DelicateCoroutinesApi


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commitNow()

        }
    }
}


//    interface onBackPressedListener {
//        fun onBackPressed()
//    }
//
//    override fun onBackPressed(){
//        val fragmentList = supportFragmentManager.fragments
//        for (fragment in fragmentList) {
//            if (fragment is onBackPressedListener) {
//                (fragment as onBackPressedListener).onBackPressed()
//                return
//            }
//        }
//    }