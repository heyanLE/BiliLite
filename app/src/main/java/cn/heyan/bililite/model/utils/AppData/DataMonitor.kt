package cn.heyan.bililite.model.utils.AppData

import android.content.Context
import cn.heyan.bililite.model.utils.HeLog
import java.io.File
import java.io.IOException

/**
 * DataMonitor
 * 一个本地数据文件工具类
 * 主要负责写入文件和读取文件
 * 路径：sdcard/Android/data/cn.heyan.bililite/file
 * Created by HeYan on 2018/2/11 0011.
 */
object DataMonitor{

    //var context:Context? = null

    /**
     * 数据保存
     * @param name String : 文件名字
     * @param data String : 数据
     * @param context Context : 上下文
     */
    fun saveFile(name:String,data:String,context: Context){


        try{
            val file = File(context.getExternalFilesDir(null).absolutePath + "/${name}.json")
            file.writeText(data)
            HeLog.i(":save->${name}\n${data}",this)
        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    /**
     * 数据读取 如果文件不存在，则新建新文件
     * @param name String : 文件名字
     * @param context Context : 上下文
     * @return 读取到的数据，如果文件不存在或者出异常，返回空字符串
     */
    fun loadFile(name: String,context: Context) : String{
        var data = ""
        try{
            val file = File(context.getExternalFilesDir(null).absolutePath + "/${name}.json")
            if(file.exists()) data = file.readText()
            else saveFile(name,"",context)
            HeLog.i(":load->${name} \n ${data}",this)
        }catch (e: IOException){
            e.printStackTrace()
        }
        return data
    }

}

