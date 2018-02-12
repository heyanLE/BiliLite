package cn.heyan.bililite.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.heyan.bililite.R
import cn.heyan.bililite.activities.NulActivity
import cn.heyan.bililite.utils.HeLog
import cn.heyan.bililite.utils.appdata.Data
import cn.heyan.bililite.utils.appdata.DataJson
import cn.heyan.bililite.utils.appdata.DataManager
import cn.heyan.bililite.utils.bilidata.BiliData
import com.tencent.bugly.crashreport.CrashReport
import java.io.PrintWriter
import java.io.StringWriter

/**
 * MainApplication
 * Created by HeYan on 2018/2/8 0008.
 */

class MainApplication():Application(){

    override fun onCreate() {
        super.onCreate()

        //注册ActivityMonitor
        ActivityMonitor.init(this)

        //初始化CrashHandler
        CrashHandler(this)

        //初始化Bugly组件用于异常上报
        CrashReport.initCrashReport(applicationContext, "de449571c6", false)

        //初始化DataJson
        DataJson.load(this)

        //初始化BiliData
        BiliData.init(this)

        //初始化ThemeMonitor
        ThemeMonitor.theme = DataJson.data.style
        when(DataJson.data.style){
            "blue" -> ThemeMonitor.style = R.style.Theme_Blue
            "pink" -> ThemeMonitor.style = R.style.Theme_Pink
            "green" -> ThemeMonitor.style = R.style.Theme_Green
            "yellow" -> ThemeMonitor.style = R.style.Theme_Yellow
            "red" -> ThemeMonitor.style = R.style.Theme_Red
            "purple" -> ThemeMonitor.style = R.style.Theme_Purple
        }


    }

}

/**
 * 异常拦截Handler
 */
class CrashHandler(context: Context):Thread.UncaughtExceptionHandler{

    val mContext = context


    /**
     * 当拦截到异常的时候执行方法（启动到NulActivity）
     */
    override fun uncaughtException(t: Thread, e: Throwable) {

        val writer = StringWriter()
        try {
            val printWriter = PrintWriter(writer)
            e.printStackTrace(printWriter)
            var cause = e.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
        }catch (e:Exception){
            e.printStackTrace()
        }

        HeLog.e("ThreadCrash -> ${(writer.toString())}",this)

        //启动NulActivity
        val intent = Intent()
        intent.setClass(mContext, NulActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(NulActivity.NUL_KEY,writer.toString())
        mContext.startActivity(intent)

        //退出APP
        ActivityMonitor.exitApp()

    }

    //设置拦截的Handler
    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

}


