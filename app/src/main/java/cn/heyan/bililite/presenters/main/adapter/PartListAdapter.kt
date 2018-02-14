package cn.heyan.bililite.presenters.main.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.model.activity.MainModel
import cn.heyan.bililite.model.utils.BiliHttp.VideoData
import cn.heyan.bililite.model.utils.HeLog
import cn.heyan.bililite.presenters.main.activities.MainActivityPresenterInt
import cn.heyan.bililite.view.main.activity.MainActivityInt
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * PartListAdapter
 * Created by HeYan on 2018/2/14 0014.
 */

class PartListAdapter(mainContext: Context)
    : BaseQuickAdapter<VideoData, BaseViewHolder>(R.layout.recy_part_list_item){

    var sort = "time"
    var context = mainContext


    override fun convert(helper: BaseViewHolder, item: VideoData) {

        helper.setText(R.id.item_fra_list_tv,item.title)
        helper.setText(R.id.item_fra_list_tv_up,item.upName)
        val img:ImageView = helper.getView(R.id.item_fra_list_iv)
        Glide.with(context)
                .load(item.pic)
                .into(img)
        val tvAll = helper.getView<TextView>(R.id.item_fra_list_down_tv_all)
        val linearDown = helper.getView<LinearLayout>(R.id.item_fra_list_down_linear)
        when(sort){
            "time" ->{
                linearDown.visibility = LinearLayout.INVISIBLE
                tvAll.visibility = LinearLayout.VISIBLE
                tvAll.text = MainModel.getTimeString(item.ctime.toLong())
            }
            "click" ->{
                linearDown.visibility = LinearLayout.VISIBLE
                tvAll.visibility = LinearLayout.INVISIBLE

                HeLog.i("item.view.toString()",item.view.toString(),this)
                HeLog.i("MainModel.getString",MainModel.getString(item.view.toString()),this)
                helper.setImageResource(R.id.item_fra_list_down_iv_fir,R.mipmap.play)
                helper.setText(R.id.item_fra_list_down_tv_fir,MainModel.getString(item.view.toString()))

                helper.setImageResource(R.id.item_fra_list_down_iv_sec,R.mipmap.danmaku)
                helper.setText(R.id.item_fra_list_down_tv_sec,MainModel.getString(item.danmaku.toString()))
            }
            "scores" ->{
                linearDown.visibility = LinearLayout.VISIBLE
                tvAll.visibility = LinearLayout.INVISIBLE
                helper.setImageResource(R.id.item_fra_list_down_iv_fir,R.mipmap.following)
                helper.setText(R.id.item_fra_list_down_tv_fir,MainModel.getString(item.reply.toString()))

                helper.setImageResource(R.id.item_fra_list_down_iv_sec,R.mipmap.play)
                helper.setText(R.id.item_fra_list_down_tv_sec,MainModel.getString(item.view.toString()))
            }
            "dm" ->{
                linearDown.visibility = LinearLayout.VISIBLE
                tvAll.visibility = LinearLayout.INVISIBLE
                helper.setImageResource(R.id.item_fra_list_down_iv_fir,R.mipmap.danmaku)
                helper.setText(R.id.item_fra_list_down_tv_fir,MainModel.getString(item.danmaku.toString()))

                helper.setImageResource(R.id.item_fra_list_down_iv_sec,R.mipmap.play)
                helper.setText(R.id.item_fra_list_down_tv_sec,MainModel.getString(item.view.toString()))
            }
            "stow" ->{
                linearDown.visibility = LinearLayout.VISIBLE
                tvAll.visibility = LinearLayout.INVISIBLE
                helper.setImageResource(R.id.item_fra_list_down_iv_fir,R.mipmap.star)
                helper.setText(R.id.item_fra_list_down_tv_fir,MainModel.getString(item.favorite.toString()))

                helper.setImageResource(R.id.item_fra_list_down_iv_sec,R.mipmap.play)
                helper.setText(R.id.item_fra_list_down_tv_sec,MainModel.getString(item.view.toString()))
            }
            "coin" ->{
                linearDown.visibility = LinearLayout.VISIBLE
                tvAll.visibility = LinearLayout.INVISIBLE
                helper.setImageResource(R.id.item_fra_list_down_iv_fir,R.mipmap.play)
                helper.setText(R.id.item_fra_list_down_tv_fir,MainModel.getString(item.view.toString()))

                helper.setImageResource(R.id.item_fra_list_down_iv_sec,R.mipmap.danmaku)
                helper.setText(R.id.item_fra_list_down_tv_sec,MainModel.getString(item.danmaku.toString()))
            }
        }


    }

}


