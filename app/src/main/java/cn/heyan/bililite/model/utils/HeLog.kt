package cn.heyan.bililite.model.utils

import android.util.Log

/**
 * HeLog 封装的Log输出工具类
 * 区别在于不用指定TAG，直接获取
 * Created by HeYan on 2018/2/11 0011.
 */

object HeLog{

    /**
     * 包装原始Log的v,d,i,w,e方法
     * 重写两个
     * 第一个直接传入msg
     * 第二个传入value在传入msg 输出为 value -> msg
     *
     * 最后一个参数是一个对象，以这个对象的类完整名（包括包名）做TAG
     */

    fun v(msg:String,classInit:Any){
        Log.v(classInit.javaClass.name,msg)
    }

    fun v(value:String,msg:String,classInit: Any){
        val msgS = value + " -> " + msg
        Log.v(classInit.javaClass.name,msgS)
    }

    fun d(msg:String,classInit:Any){
        Log.d(classInit.javaClass.name,msg)
    }

    fun d(value:String,msg:String,classInit: Any){
        val msgS = value + " -> " + msg
        Log.d(classInit.javaClass.name,msgS)
    }

    fun i(msg:String,classInit:Any){
        Log.i(classInit.javaClass.name,msg)
    }

    fun i(value:String,msg:String,classInit: Any){
        val msgS = value + " -> " + msg
        Log.i(classInit.javaClass.name,msgS)
    }

    fun w(msg:String,classInit:Any){
        Log.w(classInit.javaClass.name,msg)
    }

    fun w(value:String,msg:String,classInit: Any){
        val msgS = value + " -> " + msg
        Log.w(classInit.javaClass.name,msgS)
    }

    fun e(msg:String,classInit:Any){
        Log.e(classInit.javaClass.name,msg)
    }

    fun e(value:String,msg:String,classInit: Any){
        val msgS = value + " -> " + msg
        Log.e(classInit.javaClass.name,msgS)
    }

}
