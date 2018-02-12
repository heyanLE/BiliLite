package cn.heyan.bililite.model.utils.AppData

import android.content.Context
import cn.heyan.bililite.app.ThemeMonitor
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * DataJson
 * 一个数据处理工具类
 * 主要负责对DataMonitor读取到的String进行解析
 * Created by HeYan on 2018/2/11 0011.
 */

object DataJson{

    //String数据源
    private var dataJson = ""

    //懒加载通过数据源生成实体类对象
    val data:Data by lazy {
        ObjectMapper().readValue(dataJson,Data().javaClass)
    }

    /**
     * 从DataMonitor加载数据并解析
     * @param context Context : 上下文
     */
    fun loadJson(context: Context){

        //先读取数据并赋值给dataJson
        dataJson = DataMonitor.loadFile("Data",context)

        if (dataJson == ""){//如果读取到的数据为空
            //初始化初始数据

            //创建初始的实体类
            val dataI = Data()

            //从DataI（初始实体类）解析为json赋值给dataJson并写入文件
            dataJson = ObjectMapper().writeValueAsString(dataI)
            DataMonitor.saveFile("Data",dataJson,context)
            
            
        }

    }

    /**
     * 解析当前Data并用DataMonitor保存数据
     * @param context Context : 上下文
     */
    fun saveJson(context: Context){

        //从Data解析为json赋值给dataJson并写入文件
        dataJson = ObjectMapper().writeValueAsString(data)
        DataMonitor.saveFile("Data",dataJson,context)

    }



}

 /**
  * Data
  * 一个实体类，最终本地的数据将会转换为此类对象
  */
class Data{

     //时间线设置
     //标准格式：
    val time : Time = Time()

     //主题,值为ThemeMonitor里的ThemeId常量
    var style : String = ThemeMonitor.ThemeIdBlue

     //屏蔽的Up主，关键字，分区
     //二级实体类对象
    val shield : Shield = Shield()

}

/**
 * Shield
 * 二级实体类，由Data使用
 */
class Shield{

    //屏蔽的Up主
    //值为三级实体类对象
    val shieldUp:MutableList<ShieldUp> = mutableListOf()

    //屏蔽的关键字列表
    val shieldWord:MutableList<String> = mutableListOf()

    //屏蔽的分区列表
    //值为分区Tid
    val shieldTid:MutableList<String> = mutableListOf()

}

/**
 * ShieldUp
 * 三级实体类，由Shield使用
 */
class ShieldUp{

    //屏蔽的Up主名字
    var name = ""

    //屏蔽的Up主id
    var mid = ""

}

/**
 * Time
 * 三级实体类，由Shield使用
 */
class Time(){

    var timeBegin = "now"

    var timeEnd = "now"

}


