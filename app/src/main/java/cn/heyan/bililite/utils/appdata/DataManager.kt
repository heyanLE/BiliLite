package cn.heyan.bililite.utils.appdata

import android.content.Context

/**
 * DataManager
 * Created by eke_l on 2018/1/27 0027.
 */

object DataManager{

    /**
     * 设置时间线起点
     */
    fun setTimeFrom(timeFrom:String){
        DataJson.data.time["TimeFrom"] = timeFrom
    }

    /**
     * 设置时间线终点
     */
    fun setTimeTo(timeTo:String){
        DataJson.data.time["TimeTo"] = timeTo
    }

    /**
     * 是否已经屏蔽Up主
     */
    fun ifShieldUp(id:String):Boolean{
        for(mapS in DataJson.data.shield.shieldUp){
            if (mapS["Id"] == id) return true
        }
        return false
    }

    /**
     * 是否已经屏蔽分区
     */
    fun ifShieldTid(tid:String):Boolean{
        return DataJson.data.shield.shieldTid.contains(tid)
    }

    /**
     * 是否已经屏蔽关键字
     */
    fun ifShieldWord(word:String):Boolean{
        return DataJson.data.shield.shieldWord.contains(word)
    }

    /**
     * 移除屏蔽Up主
     * @return 添加是否成功（不存在则失败）
     */
    fun removeShieldUp(id:String):Boolean{
        if(!ifShieldUp(id)) return false
        var name = ""
        for(mapS in DataJson.data.shield.shieldUp){
            if (mapS["Id"] == id) name = mapS["Name"] as String
        }
        val map:HashMap<String,String> = HashMap()
        map["Name"] = name
        map["Id"] = id
        DataJson.data.shield.shieldUp.remove(map)
        return true
    }

    /**
     * 移除屏蔽分区
     * @return 添加是否成功（不存在则失败）
     */
    fun removeShieldTid(tid:String):Boolean{
        if(!ifShieldTid(tid)) return false
        DataJson.data.shield.shieldTid.remove(tid)
        return true
    }

    /**
     * 移除屏蔽关键字
     * @return 添加是否成功（不存在则失败）
     */
    fun removeShieldWord(word:String):Boolean{
        if(!ifShieldWord(word)) return false
        DataJson.data.shield.shieldTid.remove(word)
        return true
    }

    /**
     * 添加屏蔽Up主
     * @return 添加是否成功（已经存在则失败）
     */
    fun addShieldUp(name:String,id:String):Boolean{
        if(ifShieldUp(id)) return false
        val map:HashMap<String,String> = HashMap()
        map["Name"] = name
        map["Id"] = id
        DataJson.data.shield.shieldUp.add(map)
        return true
    }

    /**
     * 添加屏蔽分区
     * @return 添加是否成功（已经存在则失败）
     */
    fun addShieldTid(tid:String):Boolean{
        if(ifShieldTid(tid)) return false
        DataJson.data.shield.shieldTid.add(tid)
        return true
    }

    /**
     * 添加屏蔽关键字
     * @return 添加是否成功（已经存在则失败）
     */
    fun addShieldWord(word:String):Boolean{
        if(ifShieldWord(word)) return false
        DataJson.data.shield.shieldTid.add(word)
        return true
    }

    /**
     * 保存数据
     */
    fun save(context: Context){
        DataJson.save(context)
    }

}
