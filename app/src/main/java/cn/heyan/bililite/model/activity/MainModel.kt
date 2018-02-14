package cn.heyan.bililite.model.activity

import cn.heyan.bililite.R
import cn.heyan.bililite.model.utils.AppData.AppData
import cn.heyan.bililite.model.utils.BiliData.BiliData
import cn.heyan.bililite.model.utils.BiliHttp.BiliApi
import cn.heyan.bililite.model.utils.BiliHttp.HttpData
import cn.heyan.bililite.model.utils.BiliHttp.HttpGet
import cn.heyan.bililite.model.utils.BiliHttp.VideoData
import cn.heyan.bililite.presenters.main.adapter.NavData
import cn.heyan.bililite.presenters.main.adapter.PagerData
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

/**
 * MainModel
 * Created by HeYan on 2018/2/14 0014.
 */

object MainModel{

    fun getString(int: String):String{
        val intL = int.toLong()
        if (intL < 10000 ) return intL.toString()
        else return (((intL/1000).toFloat())/10).toString() + "万"
    }

    fun getTimeBeginShow():String{
        if(AppData.spData!!.timeBegin != "now")return AppData.spData!!.timeBegin.substring(0,10)
        else{
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date()
            date.time -= 1000*60*60*24*7
            return sdf.format(date).substring(0,10)
        }

    }

    fun getTimeEndShow():String{
        if(AppData.spData!!.timeEnd != "now")return AppData.spData!!.timeEnd.substring(0,10)
        else{
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date()
            return sdf.format(date).substring(0,10)
        }
    }

    fun getTimeBegin():String{
        if(AppData.spData!!.timeBegin != "now")return AppData.spData!!.timeBegin
        else{
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date()
            date.time -= 1000*60*60*24*7
            return sdf.format(date)
        }
    }

    fun getTimeEnd():String{
        if(AppData.spData!!.timeEnd != "now")return AppData.spData!!.timeEnd
        else{
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date()
            return sdf.format(date)
        }
    }


    fun getTimeString(time:Long):String{
        var string = "1分钟前投递"
        val nowTime = (Date().time/1000)

        val data = Date()
        data.time = time*1000

        val cal = Calendar.getInstance()
        cal.time = (data)

        val calN = Calendar.getInstance()
        calN.time = Date()
        val diffS = nowTime - time
        if (diffS > 60) {

            if (diffS <= 60*60)
                string = "${diffS/60}分钟前投递"
            else if (diffS <= 60*60*24)
                string = "${diffS/60/60}小时前投递"
            else {
                val yDiff = (calN[Calendar.YEAR] - cal[Calendar.YEAR])
                val mDiff = (calN[Calendar.MONTH] - cal[Calendar.MONTH])
                val dDiff = (calN[Calendar.DAY_OF_YEAR] - cal[Calendar.DAY_OF_YEAR])

                if (yDiff > 0) string = "${(yDiff)}年前投递"
                else if(mDiff > 0) string = "${(mDiff)}月前投递"
                else if(dDiff > 0) string = "${(dDiff)}天前投递"
                else string = "1分钟前投递"
            }

        }

        return string
    }

    fun getNetWortData(page:String,tid:Int): Observable<MutableList<VideoData>> {
        return HttpGet.getData(BiliApi.getPartListNew(page,tid.toString()),HttpData.TYPE_NEW)
    }

    fun getNetWortData(page:String,tid:Int,sort:String)
            : Observable<MutableList<VideoData>> {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeFrom = sdf.parse(getTimeBegin())
        val timeTo = sdf.parse(getTimeEnd())
        val spff = SimpleDateFormat("yyyyMMdd")
        spff.format(timeFrom)
        val url = BiliApi.getPartListHot(page,tid.toString(),sort,spff.format(timeFrom),spff.format(timeTo))
        return HttpGet.getData(url,HttpData.TYPE_HOT)
    }

    fun getNameByTid(tid:Int):String{
        var name = ""
        for (partX in BiliData.part.part) {
            if (partX.tid == tid) {
                name = partX.name
                break
            }
            for (children in partX.children)
                if (tid == children.tid) {
                    name = children.name
                    break
                }
        }

        return name
    }

    fun getPartDataList(tid:Int):MutableList<PagerData>{

        val list:MutableList<PagerData> = mutableListOf()

        for (part in BiliData.part.part){
            if (part.tid == tid){

                for (child in part.children){
                    val pd = PagerData()
                    pd.tid = child.tid
                    pd.title = child.name
                    list.add(pd)
                }
                break

            }
        }

        return list

    }

    fun getNavDataList(p:Int):MutableList<NavData>{

        val list:MutableList<NavData> = mutableListOf()

        for (part in BiliData.part.part){

            val navData = NavData()
            navData.name = part.name
            navData.tid = part.tid
            when(part.name){
                "番剧" -> navData.img = R.mipmap.ic_category_t13
                "国创" -> navData.img = R.mipmap.ic_category_t167
                "动画" -> navData.img = R.mipmap.ic_category_t1
                "音乐" -> navData.img = R.mipmap.ic_category_t3
                "舞蹈" -> navData.img = R.mipmap.ic_category_t129
                "游戏" -> navData.img = R.mipmap.ic_category_t4
                "科技" -> navData.img = R.mipmap.ic_category_t36
                "生活" -> navData.img = R.mipmap.ic_category_t160
                "鬼畜" -> navData.img = R.mipmap.ic_category_t119
                "时尚" -> navData.img = R.mipmap.ic_category_t155
                "广告" -> navData.img = R.mipmap.ic_category_t165
                "娱乐" -> navData.img = R.mipmap.ic_category_t5
            }

            navData.ifCho = false


            list.add(navData)

        }

        if ((list.size - 1) >= p) list[p].ifCho = true

        return list
    }

}
