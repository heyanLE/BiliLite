package cn.heyan.bililite.utils

import android.content.Context
import android.util.Log
import java.io.*

/**
 * DataMonitor
 * Created by eke_l on 2018/1/26 0026.
 */

object DataMonitor{

    //companion object {

        val TAG = "DataMonitor"

        /**
         * 保存数据
         */
        fun saveFile(name:String,data:String,context:Context){


            try{
                val file = File(context.getExternalFilesDir(null).absolutePath + "/${name}.json")
                file.writeText(data)
                Log.i(TAG,":save->${name}\n${data}")
            }catch (e:IOException){
                e.printStackTrace()
            }

        }

        /**
         * 读取数据
         */
        fun loadFile(name: String,context: Context) : String{
            var data = ""
            try{
                val file = File(context.getExternalFilesDir(null).absolutePath + "/${name}.json")
                if(file.exists()) data = file.readText()
                else saveFile(name,"",context)
                Log.i(TAG,":load->${name} \n ${data}")
            }catch (e:IOException){
                e.printStackTrace()
            }
            return data
        }

    //}

}
