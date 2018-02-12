package cn.heyan.bililite.utils

import android.util.Log

/**
 * HeLog
 * Created by HeYan on 2018/2/8 0008.
 */

/**
 * 封装的Log类
 */
object HeLog{

    /**
     * 通过传入一个Any对象
     * 取类名做TAG
     */

    fun i(msg:String,classI:Any){
        Log.i(classI.javaClass.name,msg)
    }

    fun v(msg:String,classI:Any){
        Log.v(classI.javaClass.name,msg)
    }

    fun d(msg:String,classI: Any){
        Log.d(classI.javaClass.name,msg)
    }

    fun w(msg: String,classI: Any){
        Log.w(classI.javaClass.name,msg)
    }

    fun e(msg: String,classI: Any){
        Log.e(classI.javaClass.name,msg)
    }


}
