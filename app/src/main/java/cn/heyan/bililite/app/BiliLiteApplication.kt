package cn.heyan.bililite.app

import android.app.Application
import android.content.Context
import android.content.Intent
import cn.heyan.bililite.model.utils.AppData.AppData
import cn.heyan.bililite.model.utils.BiliData.BiliData
import cn.heyan.bililite.view.activities.NulActivity
import cn.heyan.bililite.model.utils.HeLog
import com.tencent.bugly.crashreport.CrashReport
import java.io.PrintWriter
import java.io.StringWriter

/**
 * BiliLiteApplication
 * 替换默认的Application以支持主体框架 - bug ly异常拦截 以及 model 层初始化
 * Created by HeYan on 2018/2/11 0011.
 */

class BiliLiteApplication:Application(){

    override fun onCreate() {
        super.onCreate()

        //初始化Bug ly
        CrashReport.initCrashReport(applicationContext, "de449571c6", true  )

        //初始化AppData
        AppData.init(this)

        //初始化BiliData
        BiliData.init(this)

        //初始化CrashHandler
        CrashHandler(this)

        //初始化ThemeMonitor
        ThemeMonitor.initTheme()

        //初始化ActivityMonitor
        ActivityMonitor.init(this)

        setTheme(ThemeMonitor.nowStyleId)

        //AppData.addShieldTid(13)

    }

}

/**
 * 异常拦截Handler
 */
class CrashHandler(context: Context):Thread.UncaughtExceptionHandler{

    private val context = context


    /**
     * 当拦截到异常的时候执行方法（启动到NulActivity）
     */
    override fun uncaughtException(t: Thread, e: Throwable) {

        //打印错误信息
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

        //HeLog输出错误信息
        HeLog.e("ThreadCrash -> ${(writer.toString())}",this)

        //启动NulActivity
        val intent = Intent()
        intent.setClass(context, NulActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(NulActivity.NUL_KEY,writer.toString())
        context.startActivity(intent)

        //退出APP
        ActivityMonitor.exitApp()

    }

    //设置拦截的Handler
    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

}



