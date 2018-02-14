package cn.heyan.bililite.app

import android.app.Activity
import android.content.Intent
import cn.heyan.bililite.R
import cn.heyan.bililite.model.utils.AppData.AppData
import cn.heyan.bililite.model.utils.HeLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * ThemeMonitor 主题管理类
 * Created by HeYan on 2018/2/11 0011.
 */

object ThemeMonitor{

    //主题Id（因为R.style.xx  的id会变，所以需要用常量转换）
    val ThemeIdBlue = "胖次蓝"
    val ThemeIdPink = "少女粉"
    val ThemeIdRed = "姨妈红"
    val ThemeIdYellow = "咸蛋黄"
    val ThemeIdGreen = "原谅绿"
    val ThemeIdPurple = "基佬紫"
    val ThemeIdBlack = "寡妇黑"
    val ThemeIdGray = "小灰灰"

    //重启前台Activity时候传入数据的Key
    val THEME_EXTRA_KEY = "themeCacheData"

    //map<ThemeId,StyleId>
    val mapId2Style:HashMap<String,Int> = HashMap()

    //现在正在使用的ThemeId和StyleId
    var nowStyleId = R.style.Theme_Blue
    var nowThemeId = ThemeIdBlue


    init {
        //初始化mapId2Style的数据
        mapId2Style[ThemeIdBlue] = R.style.Theme_Blue
        mapId2Style[ThemeIdPink] = R.style.Theme_Pink
        mapId2Style[ThemeIdRed] = R.style.Theme_Red
        mapId2Style[ThemeIdYellow] = R.style.Theme_Yellow
        mapId2Style[ThemeIdGreen] = R.style.Theme_Green
        mapId2Style[ThemeIdPurple] = R.style.Theme_Purple
        mapId2Style[ThemeIdBlack] = R.style.Theme_Black
        mapId2Style[ThemeIdGray] = R.style.Theme_Gray

    }

    fun initTheme(){
        nowThemeId = AppData.spData!!.theme
        nowStyleId = mapId2Style[nowThemeId] as Int
    }

    /**
     * 设置新的主题
     * @param themeId String : ThemeId 值必须为上面的ThemeId开头的常量
     * @param data String : 要给栈内第一个重启Activity的数据
     */
    fun setNewTheme(themeId : String , data : String){

        nowThemeId = themeId
        nowStyleId = mapId2Style[themeId] as Int

        AppData.spData?.theme = themeId
        AppData.spData?.save()

        //创建多一个返回栈对象，把原来的元素全部加入
        val listN:MutableList<Activity> = mutableListOf()
        listN.addAll(ActivityMonitor.ActivityList)

        HeLog.i("Al.size",ActivityMonitor.ActivityList.size.toString(),this)

        //把最后一个Activity提取出来
        val activity = listN[listN.size - 1]

        //移除最后一个返回栈
        listN.removeAt(listN.size - 1)





        //以返回栈为数据源创建被观察者
        //fromIterable是指一个一个发射
        Observable.fromIterable(listN).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //观察后把数据源里的activity全部recreate
                    it.recreate()
                    //Log输出
                    HeLog.i(it.javaClass.name,"onRecreate",this)
                }

        //重启栈内第一个Activity
        //为了防止recreate出现的闪光
        val intent = Intent(activity, activity.javaClass)
        activity.application.setTheme(nowStyleId)
        intent.putExtra(THEME_EXTRA_KEY, data)//向intent假如数据
        activity.startActivity(intent)
        activity.overridePendingTransition(0,0)//activity跳转动画
        activity.finish()

    }



}
