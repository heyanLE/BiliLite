package cn.heyan.bililite.presenters.main.adapter

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.model.activity.MainModel

/**
 * NevAdapter
 * Created by HeYan on 2018/2/14 0014.
 */

class NavAdapter(context: Context,p:Int):BaseAdapter(){

    val dataList:MutableList<NavData> = mutableListOf()

    private val mainContext = context

    var title = ""
    var tid = 0

    init {
        dataList.addAll(MainModel.getNavDataList(p))
        title = dataList[p].name
        tid = dataList[p].tid
    }

    fun newCho(p:Int){
        for (data in dataList) data.ifCho = false
        dataList[p].ifCho = true
        title = dataList[p].name
        tid = dataList[p].tid
        notifyDataSetChanged()
    }

    fun newCho(tid:Int,p2:Boolean){
        for (data in dataList)
            if (data.tid == tid)
                newCho(dataList.indexOf(data))
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val holder:ViewHolder ?
        var convertViewN = p1

        if(convertViewN == null){
            val inflater = LayoutInflater.from(mainContext)
            convertViewN = inflater.inflate(R.layout.grid_main_item_nav,null)
            holder = ViewHolder(convertViewN.findViewById(R.id.item_grid_tv)
                    ,convertViewN.findViewById(R.id.item_grid_iv))
            convertViewN!!.tag = holder
        }else{
            holder = convertViewN.tag as ViewHolder
        }

        holder.imageView.setImageResource(dataList[p0].img)
        holder.textView.text = dataList[p0].name

        if(dataList[p0].ifCho)
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(mainContext.resources
                    , R.color.chose,null))
        else
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(mainContext.resources
                    , R.color.no,null))

        return convertViewN
    }

    override fun getItemId(p0: Int): Long {
        return dataList[p0].tid.toLong()
    }

}

class NavData{

    var img = 0
    var name = ""
    var ifCho = false
    var tid = 0

}

internal class ViewHolder(tv: TextView, iv: ImageView) {
    var textView: TextView = tv
    var imageView: ImageView = iv
}
