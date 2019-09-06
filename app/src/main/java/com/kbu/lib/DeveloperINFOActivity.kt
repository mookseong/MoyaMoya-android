package com.kbu.lib

import android.util.Log
import android.widget.Toast
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.InterstitialAd
//import com.google.android.gms.ads.MobileAds
import com.kbu.lib.Base.BaseActivity
import kotlinx.android.synthetic.main.activity_developer_info.*


abstract class DeveloperINFOActivity : BaseActivity(R.layout.activity_developer_info){

    override fun settingActivity() {
        Toast.makeText(this,"안녕하세요! 방갑습니다", Toast.LENGTH_LONG).show()

    }
}
