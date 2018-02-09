package cn.heyan.bililite.netword

/**
 * BiliApi
 * Created by HeYan on 2018/2/4 0004.
 */
 object BiliApi{

    val sortMap:HashMap<String,String> = HashMap()

    init {
        sortMap["播放"] = "click"
        sortMap["评论"] = "scores"
        sortMap["弹幕"] = "dm"
        sortMap["收藏"] = "scores"
        sortMap["硬币"] = "coin"

    }

    fun getPartListHot(page:String,part:String,sort:String,timeFrom:String,timeTo:String)
            = "https://s.search.bilibili.com/cate/search?callback=result&main_ver=v3&search_type=video&view_type=hot_rank&order=${sort}&copy_right=-1&cate_id=${part}&page=${page}&pagesize=20&jsonp=json&time_from=${timeFrom}&time_to=${timeTo}&_=1517731620677"


    fun getPartListNew(page:String,part:String)
            = "https://api.bilibili.com/x/web-interface/newlist?callback=result&rid=${part}&type=0&pn=${page}&ps=20&jsonp=json&_=1517732153837"
}
