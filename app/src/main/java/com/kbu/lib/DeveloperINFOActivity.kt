package com.kbu.lib

import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.kbu.lib.Base.BaseActivity
import kotlinx.android.synthetic.main.activity_developer_info.*


class DeveloperINFOActivity : BaseActivity(R.layout.activity_developer_info){
    private lateinit var mInterstitialAd: InterstitialAd
    override fun set() {
        MobileAds.initialize(this, "ca-app-pub-3042326807242532~3097610906")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3042326807242532/8896732495"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        Toast.makeText(this,"안녕하세요! 방갑습니다", Toast.LENGTH_LONG).show()
;
        img_pay.setOnClickListener {
            Toast.makeText(this,"이미지 광고 보기", Toast.LENGTH_LONG).show()
            if (mInterstitialAd.isLoaded)
                mInterstitialAd.show()
            else
                Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
        video_pay.setOnClickListener {
            Toast.makeText(this,"동영상 광고 보기", Toast.LENGTH_LONG).show()
        }
        coffee_pay.setOnClickListener {
            Toast.makeText(this,"아직 기능을 못만들었어요ㅠㅠ\n지금은 마음만 받을게요!!", Toast.LENGTH_LONG).show()
        }
        lunch_pay.setOnClickListener {
            Toast.makeText(this,"아직 기능을 못만들었어요ㅠㅠ\n지금은 마음만 받을게요!!", Toast.LENGTH_LONG).show()
        }

    }
}
