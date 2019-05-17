package com.kbu.lib.Base

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.os.Build
import android.support.v7.app.AlertDialog

abstract class BaseActivity(private val LayoutID: Int) : AppCompatActivity() {
    val libURL = "http://lib.bible.ac.kr"
    val positiveButtonClick = { dialog: DialogInterface, which: Int -> finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutID)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        if (!checkNetworkService()) {
            with(AlertDialog.Builder(this))
            {
                setTitle("인터넷이 연결되어있지않습니다.")
                setMessage("인터넷이 연결되어 있지 않거나 연결에 문제가 발생하였습니다.\n나중에 다시 시도하시거나 계속 문제가 발생된다면 mookseong147@gmail.com으로 문의해주시기 바랍니다.")
                setPositiveButton("종료", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
        } else
            settingActivity()
    }

    private fun checkNetworkService(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable
        val isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting
        val isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable
        val isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting

        return isWifiAvailable && isWifiConnect || isMobileAvailable && isMobileConnect
    }

    abstract fun settingActivity()

    abstract fun setEvent()

}