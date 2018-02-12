package cn.heyan.bililite.model.utils.BiliData

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * BiliData
 * 用于解析assets内的BiliData.json
 * Created by HeYan on 2018/2/11 0011.
 */
object BiliData{

    //String数据源
    var jsonS = ""

    //通过懒加载获取数据实体类
    val part:Part by lazy{
        ObjectMapper().readValue(jsonS,Part().javaClass)
    }

    //初始化 读取数据源并赋值给jsonS
    fun init(context: Context){
        jsonS = BufferedReader(InputStreamReader(context.assets.open("BiliData.json")))
                .readText()
    }

}

 /**
  * Part
  * 数据实体类
  */
class Part{

    val part:MutableList<PartX> = mutableListOf()

}

 /**
  * 二级实体类
  * 由Part使用
  */
class PartX{

    var name = ""
    var tid = 0
    var reid = 0
    val children:MutableList<Children> = mutableListOf()

}

/**
 * 三级实体类
 * 由PartX使用
 */
class Children{

    var tid = 0
    var reid = 0
    var name = ""
    var logo = ""

}