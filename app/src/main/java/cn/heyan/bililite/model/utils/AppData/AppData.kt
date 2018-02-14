package cn.heyan.bililite.model.utils.AppData

import android.content.Context

/**
 * AppData
 * 一个封装类，主要对DataJson的功能进行封装，也是对AppData整个包的
 * 功能进行封装
 * Created by HeYan on 2018/2/11 0011.
 */

object AppData{
    
    //=================================
    //数据读取与保存


    /**
     * 初始化AppData
     * @param context Context : 上下文
     */
    fun init(context: Context){
        //初始化SQLiteHelper
        SQLiteHelper.init(context)
        //初始化SPHelper
        SPHelper.init(context)
    }

    
    
    //===================================
    //数据处理
    
    /**
     * 实体数据对象
     * 直接获取，设置
     */

    val shieldUpList:MutableList<ShieldUp>
        get() = SQLiteHelper.sQLite!!.getUpList()

    val shieldWordList:MutableList<String>
        get() = SQLiteHelper.sQLite!!.getWordList()

    val shieldTidList:MutableList<Int>
        get() = SQLiteHelper.sQLite!!.getTidList()

    val spData:SP?
        get() = SPHelper.sP

    //==================================
    fun addShieldUp(shieldUp: ShieldUp){
        SQLiteHelper.sQLite!!.addUp(shieldUp)
    }

    fun addShieldWord(shieldWord:String){
        SQLiteHelper.sQLite!!.addWord(shieldWord)
    }

    fun addShieldTid(shieldTid:Int){
        SQLiteHelper.sQLite!!.addTid(shieldTid)
    }

    fun deleteShieldUpByName(name:String){
        SQLiteHelper.sQLite!!.deleteUp(name)
    }

    fun deleteShieldUpByMid(mid:Int){
        SQLiteHelper.sQLite!!.deleteUp(mid)
    }

    fun deleteShieldWord(word:String){
        SQLiteHelper.sQLite!!.deleteWord(word)
    }

    fun deleteShieldTid(tid:Int){
        SQLiteHelper.sQLite!!.deleteTid(tid)
    }



}
