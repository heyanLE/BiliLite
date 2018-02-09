package cn.heyan.bililite.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.app.ThemeMonitor
import kotlinx.android.synthetic.main.activity_theme.*
import android.os.Build



/**
 * ThemeActivity
 * Created by HeYan on 2018/2/8 0008.
 */

class ThemeActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_theme)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }

        setSupportActionBar(theme_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        theme_toolbar.setNavigationOnClickListener {
            finish()
        }

        when(intent.getStringExtra(ThemeMonitor.THEME_EXTRA_KEY)){
            "blue" -> {
                theme_blue_tv.text = "已使用"
            }
            "pink" ->{
                theme_pink_tv.text = "已使用"
            }
            "green" -> {
                theme_green_tv.text = "已使用"
            }
            "yellow" -> {
                theme_yellow_tv.text = "已使用"
            }
            "red" -> {
                theme_red_tv.text = "已使用"
            }
            "purple" -> {
                theme_purple_tv.text = "已使用"
            }
        }

        theme_blue.setOnClickListener {
            ThemeMonitor.setTheme(this,"blue","blue")
        }
        theme_pink.setOnClickListener {
            ThemeMonitor.setTheme(this,"pink","pink")
        }
        theme_yellow.setOnClickListener {
            ThemeMonitor.setTheme(this,"yellow","yellow")
        }
        theme_red.setOnClickListener {
            ThemeMonitor.setTheme(this,"red","red")
        }
        theme_green.setOnClickListener {
            ThemeMonitor.setTheme(this,"green","green")
        }
        theme_purple.setOnClickListener {
            ThemeMonitor.setTheme(this,"purple","purple")
        }


    }

}
