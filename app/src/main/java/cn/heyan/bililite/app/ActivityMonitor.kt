package cn.heyan.bililite.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.heyan.bililite.model.utils.HeLog

/**
 * ActivityMonitor Activity管理类
 * 拥有一个返回栈 实现了Activity生命周期监听接口
 * 在Activity在OnCreate周期时setTheme
 * Created by HeYan on 2018/2/11 0011.
 */

object ActivityMonitor: Application.ActivityLifecycleCallbacks{

    //Activity返回栈
    val ActivityList:MutableList<Activity> = mutableListOf()

    /**
     * finish所有的Activity
     * 清空返回栈
     */
    fun finishAllActivity(){
        for (activity in ActivityList)
            if ( ! activity.isFinishing )
                activity.finish()

        ActivityList.clear()
    }

    /**
     * 退出App，先finish所有Activity再Kill进程
     */
    fun exitApp(){
        finishAllActivity()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * =============================================================
     * 重写Activity生命周期监听方法
     * HeLog输出
     */
    
    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        p0?.let{
            HeLog.i(it.javaClass.name,"onCreated",this)

            //==========
            //设置OnCreate的Activity的Theme为当前的主题
            it.setTheme(ThemeMonitor.mapId2Style[ThemeMonitor.nowThemeId] as Int)
            //添加到返回栈
            ActivityList.add(it)
        }

    }

    override fun onActivityDestroyed(p0: Activity?) {
        p0?.let{
            HeLog.i(it.javaClass.name,"onDestroyed",this)

            //=============
            //从返回栈移除
            ActivityList.remove(it)
        }
    }

    override fun onActivityResumed(p0: Activity?) {
        p0?.let {
            HeLog.i(it.javaClass.name,"onResumed",this)
        }
    }

    override fun onActivityPaused(p0: Activity?) {
        p0?.let {
            HeLog.i(it.javaClass.name,"onPaused",this)
        }
    }

    override fun onActivityStarted(p0: Activity?) {
        p0?.let {
            HeLog.i(it.javaClass.name,"onStarted",this)
        }
    }

    override fun onActivityStopped(p0: Activity?) {
        p0?.let {
            HeLog.i(it.javaClass.name,"onStopped",this)
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        p0?.let {
            HeLog.i(it.javaClass.name,"onSaveInstanceState",this)
        }
    }

}
