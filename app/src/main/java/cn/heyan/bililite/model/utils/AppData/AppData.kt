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
        //初始化DataJson
        DataJson.loadJson(context)
    }
    
    /**
     * 保存当前更改的数据
     * @param context Context : 上下文
     */
    fun save(context: Context){
        //保存数据
        DataJson.saveJson(context)
    }
    
    
    //===================================
    //数据处理
    
    /**
     * 实体数据对象
     * 直接获取，设置
     */

    val data : Data
    get() = DataJson.data

    var timeBegin : String
    get() = data.time.timeBegin
    set(value) {
        data.time.timeBegin = value
    }

    var timeEnd : String
    get() = data.time.timeEnd
    set(value) {
        data.time.timeEnd = value
    }

    var style : String
    get() = data.style
    set(value) {
        data.style = value
    }

    private val shield : Shield
    get() = data.shield

    val shieldWord : MutableList<String>
    get() = shield.shieldWord

    val shieldTid : MutableList<String>
    get() = shield.shieldTid

    val shieldUp : MutableList<ShieldUp>
    get() = shield.shieldUp

    //新的ShieldUp对象
    val newShieldUp:ShieldUp
    get() = ShieldUp()

}
