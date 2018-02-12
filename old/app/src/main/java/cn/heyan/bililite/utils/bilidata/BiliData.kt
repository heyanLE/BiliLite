package cn.heyan.bililite.utils.bilidata

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * BiliData
 * Created by eke_l on 2018/1/27 0027.
 */

object BiliData{

    var jsonS = ""

    val part:Part by lazy{
        ObjectMapper().readValue(jsonS,Part().javaClass)
    }

    fun init(context: Context){
        jsonS = BufferedReader(InputStreamReader(context.assets.open("BiliData.json")))
                .readText()
    }

}

class Part{

    val part:HashMap<String,PartX> = HashMap()

}

class PartX{

    var name = ""
    var tid = 0
    var reid = 0
    val children:MutableList<HashMap<String,String>> = mutableListOf()

}
