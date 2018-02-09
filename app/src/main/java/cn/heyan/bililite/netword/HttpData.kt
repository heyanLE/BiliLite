package cn.heyan.bililite.netword

import cn.heyan.bililite.utils.HeLog
import cn.heyan.bililite.utils.appdata.DataJson.data
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * HttpData
 * Created by HeYan on 2018/2/4 0004.
 */

object HttpData{

    val TYPE_HOT = 0
    val TYPE_NEW = 1


    fun loadJson(json:String,type:Int):MutableList<VideoData>?{

        try {


            val result: MutableList<VideoData> = mutableListOf()

            when (type) {
                TYPE_HOT -> {

                    val jsonO = ObjectMapper().readValue(json, jsonH().javaClass)

                    for (data in jsonO.result) {
                        val vd = VideoData()
                        vd.aid = data["id"].toString()
                        vd.title = data["title"].toString()
                        vd.pic = data["pic"].toString()
                        vd.desc = data["description"].toString()
                        vd.upName = data["author"].toString()
                        vd.pubdate = data["pubdate"].toString()
                        val sc = (data["senddate"].toString())
                        val date = Date((sc + "000").toLong())
                        val ctim = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
                        vd.ctime = ctim

                        vd.pageN = jsonO.numpages
                        vd.nowP = jsonO.page

                        vd.favorite = (data["favorites"].toString()).toInt()
                        vd.reply = (data["review"].toString()).toInt()
                        vd.danmaku = (data["video_review"].toString()).toInt()
                        result.add(vd)
                    }

                }

                TYPE_NEW -> {

                    HeLog.i(json, this)
                    val jsonO = ObjectMapper().readValue(json, jsonR().javaClass)
                    val archivess = jsonO.result["archives"] as MutableList<HashMap<String, Any>>

                    val page = jsonO.result["page"] as HashMap<String, Int>
                    val pageN = page["num"] as Int

                    val count = page["count"] as Int
                    val size = page["size"] as Int

                    val alPag = size / count + 1

                    for (archives in archivess) {
                        val vd = VideoData()
                        vd.aid = (archives["aid"].toString())
                        //vd.tid = (archives["tid"] as String)
                        vd.title = (archives["title"].toString())
                        vd.pic = (archives["pic"].toString())
                        vd.desc = (archives["desc"].toString())
                        val s = (archives["pubdate"].toString())
                        val data = Date((s + "000").toLong())
                        vd.pubdate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data)

                        val sc = (archives["ctime"].toString())
                        val ctim = Date((sc + "000").toLong())
                        vd.ctime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ctim)

                        val owner = archives["owner"] as HashMap<String, String>
                        vd.upName = owner["name"].toString()

                        val stat = archives["stat"] as HashMap<String, Int>
                        vd.view = stat["view"] as Int
                        vd.danmaku = stat["danmaku"] as Int
                        vd.reply = stat["reply"] as Int
                        vd.favorite = stat["favorite"] as Int
                        vd.coin = stat["coin"] as Int

                        vd.nowP = pageN
                        vd.pageN = alPag
                        result.add(vd)
                    }
                }
            }

            return result
        }catch (e:Exception){
            e.printStackTrace()
            return null
        }

    }


}

class jsonR{
    @JsonProperty("data")
    val result:HashMap<String,Any> = HashMap()
    var code:Int = 0
    var message = ""
    var ttl = 1
}

class jsonH{
    val result:MutableList<HashMap<String,String>> = mutableListOf()
    var exp_list:String? = null
    var numpages = 0
    var code = 0
    val cost_time:HashMap<String,String> = HashMap()
    var pagesize = 20
    var suggest_keyword = ""
    var egg_info:String? = null
    var numResults = 113
    var seid = "3189148639587918549"
    var msg = "success"
    var egg_hit = 0
    var rqt_type = "search"
    var page = 1
}

class VideoData{

    /**
     * Av号
     * 标题
     * 封面地址
     * 简介
     * 发布时间
     * 投递时间
     */
    var aid = ""
    var title = ""
    var pic = ""
    var desc = ""
    var pubdate = ""
    var ctime = ""

    /**
     * Up主  名字
     *      mid
     *      头像
     */
    var upName = ""

    /**
     * 播放数
     * 弹幕数
     * 评论数
     * 收藏数
     * 硬币数
     */
    var view = 0
    var danmaku = 0
    var reply = 0
    var favorite = 0
    var coin = 0

    /**
     * 当前页数
     * 总页数
     */
    var nowP = 0
    var pageN = 0



}
