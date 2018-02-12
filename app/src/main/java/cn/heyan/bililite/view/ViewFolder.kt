package cn.heyan.bililite.view

import android.content.Context
import android.view.View

/**
 * ViewFolder
 * View文件夹实体类
 * View层通过新建这个实体类的对象来把view传到presenters层
 * Created by HeYan on 2018/2/11 0011.
 */

class ViewFolder(context: Context){

    //一个装着View的Map
    //格式{控件Id,View对象}
    val viewMap:HashMap<Int, View> = HashMap()

    //上下文对象，View层资源的上下文对象
    val context:Context = context

}
