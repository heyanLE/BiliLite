package cn.heyan.bililite.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.heyan.bililite.R
import kotlinx.android.synthetic.main.activity_nul_layout.*

/**
 * NulActivity
 * Created by HeYan on 2018/2/8 0008.
 */

class NulActivity:AppCompatActivity(){

    companion object {
        val NUL_KEY = "NulLog"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nul_layout)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }

        var nul = ""

        if (intent != null){
            try {
                nul = intent.getStringExtra(NUL_KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        activity_nul_tv_model.text = "手机型号: ${(android.os.Build.MODEL)}"
        activity_nul_tv_android.text = "安卓版本: ${android.os.Build.VERSION.RELEASE}"
        activity_nul_tv_nul.text = nul

    }

}

