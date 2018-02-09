package cn.heyan.bililite.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.heyan.bililite.utils.HeLog

/**
 * ActivityMonitor
 * Created by HeYan on 2018/2/8 0008.
 */


/**
 * 管理所有的Activity
 */
object ActivityMonitor : Application.ActivityLifecycleCallbacks{

    /**
     * Activity栈，记录所有的Activity
     */
    val activityList = mutableListOf<Activity>()


    /**
     * 初始化
     * @param application Application对象
     */
    fun init(application: Application){
        application.registerActivityLifecycleCallbacks(this)
    }


    /**
     * 向栈里添加Activity
     * @param activity 要添加到栈里的Activity对象
     */
    fun addActivity(activity: Activity){
        activityList.add(activity)
    }

    /**
     * 从栈里移除Activity
     * @param activity 要移除的Activity对象
     */
    fun removeActivity(activity: Activity){
        activityList.remove(activity)
    }

    /**
     * finish所有栈里的Activity
     */
    fun finishAllActivity(){
        for(activity in activityList)
            if(! activity.isFinishing)
                activity.finish()
        activityList.clear()
    }

    /**
     * 退出App（先finish所有Activity 再 清除进程）
     */
    fun exitApp(){
        finishAllActivity()
        android.os.Process.killProcess(android.os.Process.myPid())
    }


    /**
     * Activity生命周期监听重写
     * Log输出
     */
    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        p0?.let{
            addActivity(it)
            HeLog.i("${(it.javaClass.name)} is OnCreated",this)
            it.setTheme(ThemeMonitor.style)
        }

    }

    override fun onActivityDestroyed(p0: Activity?) {
        p0?.let{
            removeActivity(it)
            HeLog.i("${(it.javaClass.name)} is OnDestroyed",this)
        }
    }

    override fun onActivityResumed(p0: Activity?) {
        p0?.let {
            HeLog.i("${(it.javaClass.name)} is OnResumed", this)
        }
    }

    override fun onActivityPaused(p0: Activity?) {
        p0?.let {
            HeLog.i("${(it.javaClass.name)} is OnPaused", this)
        }
    }

    override fun onActivityStarted(p0: Activity?) {
        p0?.let {
            HeLog.i("${(it.javaClass.name)} is OnStarted", this)
        }
    }

    override fun onActivityStopped(p0: Activity?) {
        p0?.let {
            HeLog.i("${(it.javaClass.name)} is OnStopped", this)
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        p0?.let {
            HeLog.i("${(it.javaClass.name)} is OnSaveInstanceState", this)
        }
    }



}
