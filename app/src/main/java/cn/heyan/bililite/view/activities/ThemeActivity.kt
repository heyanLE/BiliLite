package cn.heyan.bililite.view.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import cn.heyan.bililite.R
import cn.heyan.bililite.app.ThemeMonitor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_theme_layout.*

/**
 * ThemeActivity
 * Created by HeYan on 2018/2/13 0013.
 */

class ThemeActivity:AppCompatActivity(){

    val list:MutableList<ItemData> = mutableListOf()
    val adapter:Adapter by lazy {
        Adapter(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_theme_layout)

        setSupportActionBar(theme_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        theme_toolbar.setNavigationOnClickListener {
            finish()
        }

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }


        for ((name,s) in ThemeMonitor.mapId2Style){
            val item = ItemData()
            item.name = name
            item.ifUse = false
            if (name == intent.getStringExtra(ThemeMonitor.THEME_EXTRA_KEY))
                item.ifUse = true

            when(name){
                "胖次蓝" -> item.back = R.drawable.bac_blue_gradient
                "少女粉" -> item.back = R.drawable.bac_pink_gradient
                "姨妈红" -> item.back = R.drawable.bac_red_gradient
                "咸蛋黄" -> item.back = R.drawable.bac_yellow_gradient
                "原谅绿" -> item.back = R.drawable.bac_green_gradient
                "基佬紫" -> item.back = R.drawable.bac_purple_gradient
                "寡妇黑" -> item.back = R.drawable.bac_black_gradient
                "小灰灰" -> item.back = R.drawable.bac_gray_gradient
            }
            list.add(item)
        }

        adapter.setOnItemClickListener { _, _, position ->
            ThemeMonitor.setNewTheme(list[position].name,list[position].name)
        }

        activity_theme_recy_layout.adapter = adapter
        activity_theme_recy_layout.layoutManager = LinearLayoutManager(this)

    }

}

class Adapter(dataList: MutableList<ItemData>)
    : BaseQuickAdapter<ItemData, BaseViewHolder>(R.layout.recy_theme_item,dataList){

    override fun convert(helper: BaseViewHolder, item: ItemData) {
        if (item.ifUse) helper.setText(R.id.theme_tv_use,"使用中")
        else helper.setText(R.id.theme_tv_use,"使用")

        helper.setText(R.id.item_recy_theme_name,item.name)

        helper.setBackgroundRes(R.id.item_recy_theme_show,item.back)

    }

}

class ItemData(){

    var ifUse = false
    var name = ThemeMonitor.ThemeIdBlack
    var back = R.drawable.bac_black_gradient

}


