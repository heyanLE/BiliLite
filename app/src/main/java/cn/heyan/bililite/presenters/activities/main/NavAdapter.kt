package cn.heyan.bililite.presenters.activities.main

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.model.activity.main.MainGridDataItem

/**
 * NavAdapter
 * Main组件的P层的一个模块
 * Created by HeYan on 2018/2/12 0012.
 */

class NavAdapter(data:MutableList<MainGridDataItem>,context: Context):BaseAdapter(){

    private val data = data
    private val context = context

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val holder:ViewHolder ?
        var convertViewN = p1

        if(convertViewN == null){
            val inflater = LayoutInflater.from(context)
            convertViewN = inflater.inflate(R.layout.grid_main_item_nav,null)
            holder = ViewHolder(convertViewN.findViewById(R.id.item_grid_tv)
                    ,convertViewN.findViewById(R.id.item_grid_iv))
            convertViewN!!.tag = holder
        }else{
            holder = convertViewN.tag as ViewHolder
        }

        holder.imageView.setImageResource(data[p0].icon)
        holder.textView.text = data[p0].title

        if(data[p0].ifCho)
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(context.resources
                    , R.color.chose,null))
        else
            convertViewN.setBackgroundColor(ResourcesCompat.getColor(context.resources
                    , R.color.no,null))

        return convertViewN
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

}

internal class ViewHolder(textView: TextView,imageView: ImageView) {
    var textView: TextView = textView
    var imageView: ImageView = imageView
}