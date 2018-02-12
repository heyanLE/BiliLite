package cn.heyan.bililite.adapters

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.heyan.bililite.R
import cn.heyan.bililite.utils.appdata.DataManager
import cn.heyan.bililite.utils.bilidata.BiliData
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import cn.heyan.bililite.activities.MainActivity


/**
 * NavAdapter
 * Created by eke_l on 2018/1/27 0027.
 */

class NavAdapter(context: MainActivity):BaseAdapter(){

    //var nowChoseView:View ?= null

    val nameList:MutableList<String> = mutableListOf()
    val tidList:MutableList<String> = mutableListOf()
    val iconList:MutableList<Int> = mutableListOf()

    //val viewList:MutableList<View> = mutableListOf()

    val context = context

    init {
        check()

    }



    fun onItemOnClick(p:Int){

        context.nowPart = nameList[p]
        context.supportActionBar!!.title = context.nowPart

        context.reFreshPage()
    }

    fun init(){
        nameList.clear()
        tidList.clear()
        iconList.clear()
        for ((name,partx) in BiliData.part.part){
            nameList.add(partx.name)
            tidList.add(partx.tid.toString())
            when(name){
                "番剧" -> {iconList.add(R.mipmap.ic_category_t13)}
                "国创" -> {iconList.add(R.mipmap.ic_category_t167)}
                "动画" -> {iconList.add(R.mipmap.ic_category_t1)}
                "音乐" -> {iconList.add(R.mipmap.ic_category_t3)}
                "舞蹈" -> {iconList.add(R.mipmap.ic_category_t129)}
                "游戏" -> {iconList.add(R.mipmap.ic_category_t4)}
                "科技" -> {iconList.add(R.mipmap.ic_category_t36)}
                "生活" -> {iconList.add(R.mipmap.ic_category_t160)}
                "鬼畜" -> {iconList.add(R.mipmap.ic_category_t119)}
                "时尚" -> {iconList.add(R.mipmap.ic_category_t155)}
                "广告" -> {iconList.add(R.mipmap.ic_category_t165)}
                "娱乐" -> {iconList.add(R.mipmap.ic_category_t5)}
            }
        }
    }

    fun check(){

        init()
        for (i in 0 .. (tidList.size - 1) ){

            if(i > (tidList.size - 1)){
                break
            }

            if (DataManager.ifShieldTid(tidList[i])){

                nameList.removeAt(i)
                tidList.removeAt(i)
                iconList.removeAt(i)

            }
        }
    }

    override fun getCount(): Int {
        return nameList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val holder:ViewHolder ?
        var convertViewN = convertView

        if(convertViewN == null){
            val inflater = LayoutInflater.from(context)
            convertViewN = inflater.inflate(R.layout.item_grid_nav,null)
            holder = ViewHolder(convertViewN.findViewById(R.id.item_grid_tv)
                    ,convertViewN.findViewById(R.id.item_grid_iv))
            convertViewN!!.tag = holder
        }else{
            holder = convertViewN.tag as ViewHolder
        }

        holder.imageView.setImageResource(iconList[position])
        holder.textView.text = nameList[position]

        if(nameList[position] == context.nowPart)
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(context.resources
                    , R.color.chose,null))
        else
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(context.resources
                    , R.color.no,null))

        return convertViewN
    }

    override fun getItem(position: Int): Any {
        return nameList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}

internal class ViewHolder(textView: TextView,imageView: ImageView) {
    var textView: TextView = textView
    var imageView: ImageView = imageView
}