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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }

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

    private fun checkNetworkService(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.isDefaultNetworkActive
    }
}