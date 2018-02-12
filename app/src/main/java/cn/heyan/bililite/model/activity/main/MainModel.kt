package cn.heyan.bililite.model.activity.main

import cn.heyan.bililite.R
import cn.heyan.bililite.model.utils.AppData.AppData
import cn.heyan.bililite.model.utils.BiliData.BiliData
import cn.heyan.bililite.presenters.activities.main.MainPresenter

/**
 * MainModel
 * Main组件的Model层
 * 连接层 :
 * Presenter -> cn.heyan.bililite.presenters.activity.MainPresenter
 * Created by HeYan on 2018/2/12 0012.
 */

class MainModel(mainPresenter: MainPresenter) {

    /**
     * Presenters对象
     */
    val mainPresenter = mainPresenter

    val grid = Grid(this)



}

/**
 * Grid类
 * 主要负责Main组件中GridView的数据
 */
class Grid(mainModel: MainModel) {

    val data : MutableList<MainGridDataItem> = mutableListOf()

    var nowCho = 13

    val mainModel = mainModel

    fun newChose(p0:Int){

        val tid = data[p0].tid

        for (item in data){
            if (item.tid == tid) {
                item.ifCho = true
                mainModel.mainPresenter.setToolbarTitle(item.title)
            }
            else if (item.tid == nowCho){
                item.ifCho =false
            }
        }

        nowCho = data[p0].tid

    }

    init{

        val list = BiliData.part.part//Bili数据（分区id 分区名称）
        val sList = AppData.shieldTid//App数据（已经屏蔽的分区）

        //遍历List数据
        for (part in list){
            //如果这个分区没被屏蔽
            if(! sList.contains(part.tid.toString())){
                //新建实体类
                val item = MainGridDataItem()
                //添加tid标识
                item.tid = part.tid
                //操作实体类
                item.title = part.name
                //通过id找图片源
                //貌似没有更好的写法了
                when(part.name){
                    "番剧" -> item.icon = R.mipmap.ic_category_t13
                    "国创" -> item.icon = R.mipmap.ic_category_t167
                    "动画" -> item.icon = R.mipmap.ic_category_t1
                    "音乐" -> item.icon = R.mipmap.ic_category_t3
                    "舞蹈" -> item.icon = R.mipmap.ic_category_t129
                    "游戏" -> item.icon = R.mipmap.ic_category_t4
                    "科技" -> item.icon = R.mipmap.ic_category_t36
                    "生活" -> item.icon = R.mipmap.ic_category_t160
                    "鬼畜" -> item.icon = R.mipmap.ic_category_t119
                    "时尚" -> item.icon = R.mipmap.ic_category_t155
                    "广告" -> item.icon = R.mipmap.ic_category_t165
                    "娱乐" -> item.icon = R.mipmap.ic_category_t5
                }
                //选中的按钮背景黑色
                //viewToolbarTitle更改
                if (part.tid == nowCho) {
                    item.ifCho = true
                    mainModel.mainPresenter.setToolbarTitle(item.title)
                }
                data.add(item)
            }
        }

    }

}

/**
 * Main里显示分区列表的适配器数据源一个项目的实体类
 */
class MainGridDataItem{

    var title = ""
    var icon = 0
    var ifCho = false
    var tid = 0

}

