package com.kbu.lib


import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kbu.lib.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        val positiveButtonClick = { _: DialogInterface, _: Int -> finish() }

        //SDK버전을 확인하고 메뉴바를 흰색으로 변경한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }

        //인터넷 상태를 확인하고 인터넷이 연결되어있지 않다면 확인후 안내를 하고 종료를 유도
        if (!checkNetworkService()) {
            AlertDialog.Builder(this)
                .setTitle("인터넷이 연결되어있지않습니다.")
                .setMessage("인터넷이 연결되어 있지 않거나 연결에 문제가 발생하였습니다.\n나중에 다시 시도해주세요")
                .setPositiveButton(
                    "종료",
                    DialogInterface.OnClickListener(function = positiveButtonClick)
                )
                .show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commitNow()

        }
    }

    //사용자 핸드폰의 인터넷 상태를 확인한다.
    private fun checkNetworkService(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.isDefaultNetworkActive
    }
}