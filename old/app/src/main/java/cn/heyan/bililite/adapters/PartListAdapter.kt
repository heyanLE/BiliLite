package cn.heyan.bililite.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import cn.heyan.bililite.R
import cn.heyan.bililite.netword.VideoData
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

/**
 * PartListAdapter
 * Created by HeYan on 2018/2/9 0009.
 */

class PartListAdapter(context: Context, layoutId:Int,recyclerView: RecyclerView)
    : BaseQuickAdapter<VideoData, BaseViewHolder>(layoutId,null){

    val context = context


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): VideoData? {
        return data[position]
    }

    init {
        recyclerView.adapter = this
        recyclerView.layoutManager = LinearLayoutManager(context)
        openLoadAnimation()
        bindToRecyclerView(recyclerView)
        setEmptyView(R.layout.item_part_list_empty,recyclerView)
    }



    var order = "time"

    override fun convert(helper: BaseViewHolder?, item: VideoData?) {

        val iv = helper!!.getView<ImageView>(R.id.item_fra_list_iv)
        Glide.with(context)
                .load(item!!.pic)
                .into(iv)

        helper.setText(R.id.item_fra_list_tv_up,item.upName)
        helper.setText(R.id.item_fra_list_tv,item.title)

        when(order){
            "time" ->{

                helper.getView<LinearLayout>(R.id.item_fra_list_down_linear)
                        .visibility = LinearLayout.INVISIBLE

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = sdf.parse(item.ctime)

                val cal = Calendar.getInstance()
                cal.time = date

                val calN = Calendar.getInstance()
                calN.time = Date()

                val sDiff = (Date().time - date.time)/1000

                if(sDiff <= 60) helper.setText(R.id.item_fra_list_down_tv_all,"1分钟前投递")
                else if(sDiff <= (60*60))
                    helper.setText(R.id.item_fra_list_down_tv_all,"${(sDiff/60)}分钟前投递")
                else if(sDiff <= (60*60*24))
                    helper.setText(R.id.item_fra_list_down_tv_all,"${(sDiff/60/60)}小时前投递")
                else{

                    val yDiff = (calN[Calendar.YEAR] - cal[Calendar.YEAR])
                    val mDiff = (calN[Calendar.MONTH] - cal[Calendar.MONTH])
                    val dDiff = (calN[Calendar.DAY_OF_YEAR] - cal[Calendar.DAY_OF_YEAR])

                    var text = ""
                    if (yDiff > 0) text = "${(yDiff)}年前投递"
                    else if(mDiff > 0) text = "${(mDiff)}月前投递"
                    else if(dDiff > 0) text = "${(dDiff)}天前投递"
                    else text = "1分钟前投递"

                    helper.setText(R.id.item_fra_list_down_tv_all,text)

                }

            }
        }



    }
}


