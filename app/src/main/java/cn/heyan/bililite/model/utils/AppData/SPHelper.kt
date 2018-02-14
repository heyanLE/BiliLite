package cn.heyan.bililite.model.utils.AppData

import android.content.Context
import cn.heyan.bililite.app.ThemeMonitor

/**
 * SPHelper
 * 一个使用SP保存数据的工具类
 * Created by HeYan on 2018/2/13 0013.
 */

object SPHelper{

    //SP实体类
    var sP:SP? = null

    //初始化
    fun init(context: Context){
        sP = SP(context)
        sP?.load()
    }

}

class SP(context: Context){

    //获取sp
    val pref = context.getSharedPreferences("data",Context.MODE_PRIVATE)

    //实体数据
    var timeBegin = "now"
    var timeEnd = "now"

    var theme = ThemeMonitor.ThemeIdBlue

    //加载
    fun load(){
        timeBegin = pref.getString("TimeBegin","now")
        timeEnd = pref.getString("TimeEnd","now")
        theme = pref.getString("Theme",ThemeMonitor.ThemeIdBlue)
    }

    //保存
    fun save(){
        val editor = pref.edit()
        editor.clear()
        editor.putString("TimeBegin",timeBegin)
        editor.putString("TimeEnd",timeEnd)
        editor.putString("Theme",theme)
        editor.apply()
    }

}
