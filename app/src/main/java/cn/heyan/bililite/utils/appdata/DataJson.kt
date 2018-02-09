package cn.heyan.bililite.utils.appdata

import android.content.Context
import cn.heyan.bililite.R
import cn.heyan.bililite.utils.DataMonitor
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

/**
 * DataJson
 * Created by eke_l on 2018/1/27 0027.
 */

object DataJson{

    var dataJson = ""
    val data:Data by lazy{
        ObjectMapper().readValue(dataJson,Data().javaClass)
    }

    /**
     * 从文件里加载数据刷新data()
     */
    fun load(context: Context){
        if(DataMonitor.loadFile("Data",context) == ""){
            val data = Data()
            data.style = "blue"
            data.time["TimeFrom"] = "now"
            data.time["TimeTo"] = "now"
            val jsonS = ObjectMapper().writeValueAsString(data)
            DataMonitor.saveFile("Data",jsonS,context)
        }
        dataJson = DataMonitor.loadFile("Data",context)
    }

    /**
     * 把data()对应的数据保存到文件里
     */
    fun save(context: Context){
        val jsonS = ObjectMapper().writeValueAsString(data)
        DataMonitor.saveFile("Data",jsonS,context)
    }

}

class Data{

    val time : HashMap<String,String> = HashMap()

    var style : String = "blue"

    val shield : Shield = Shield()

}

class Shield{

    val shieldUp:MutableList<HashMap<String,String>> = mutableListOf()
    val shieldWord:MutableList<String> = mutableListOf()
    val shieldTid:MutableList<String> = mutableListOf()

}