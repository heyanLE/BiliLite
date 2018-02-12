package cn.heyan.bililite.app

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StyleRes
import cn.heyan.bililite.R
import cn.heyan.bililite.utils.HeLog
import cn.heyan.bililite.utils.appdata.DataJson
import cn.heyan.bililite.utils.appdata.DataManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.completable.CompletableFromObservable
import io.reactivex.internal.operators.flowable.FlowableFromObservable

/**
 * ThemeMonitor
 * Created by HeYan on 2018/2/8 0008.
 */

/**
 * 主题管理类
 */
object ThemeMonitor{

    /**
     * 通过ActivityMonitor来让后台的Activity直接ReCreate()
     * 和前台Activity重启
     * 然后再ActivityMonitor里的生命周期监听的OnCreate里设置Activity的Theme为style
     */

    val THEME_EXTRA_KEY = "themeCacheData"

    var style = 0

    var theme = "blue"

    /**
     * 设置主题
     * @param activity 当前Activity(需重新star)
     * @param theme 新的主题ID
     * @param data 重新Star Activity需要传过去的数据
     */
    fun setTheme(activity: Activity, theme:String, data:String? = null){

        /**
         * 设置当前主题
         */
        when(theme){
            "blue" -> ThemeMonitor.style = R.style.Theme_Blue
            "pink" -> ThemeMonitor.style = R.style.Theme_Pink
            "green" -> ThemeMonitor.style = R.style.Theme_Green
            "yellow" -> ThemeMonitor.style = R.style.Theme_Yellow
            "red" -> ThemeMonitor.style = R.style.Theme_Red
            "purple" -> ThemeMonitor.style = R.style.Theme_Purple
        }

        /**
         * 保存主题
         */
        DataJson.data.style = theme
        DataJson.save(activity)


        /**
         * Recreate全部后台Activity
         * 除栈内第一个以外
         */
        val list : MutableList<Activity> = mutableListOf()
        list.addAll(ActivityMonitor.activityList)
        list.removeAt(list.size - 1)
        Observable.fromArray(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    for (act in it) {
                        act.recreate()
                        HeLog.i(act.javaClass.name + "is ReCreate",this)
                    }
                }

        /**
         * 重启站内第一个Activity
         * 为了防止recreate出现的闪光
         */
        val intent = Intent(activity, activity.javaClass)
        intent.putExtra(THEME_EXTRA_KEY, data)
        activity.startActivity(intent)
        activity.overridePendingTransition(0, 0);//移除activity跳转动画
        activity.finish()

    }

}
