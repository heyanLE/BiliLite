package cn.heyan.bililite.presenters.activities.main

import android.view.View
import android.widget.AdapterView
import cn.heyan.bililite.model.activity.main.MainModel
import cn.heyan.bililite.view.activities.main.MainActivity

/**
 * MainPresenters
 * Main组件的Presenters层
 * 这个类连接Main组件的View层和model层
 * 我把他叫Main后台操作者
 * 用于处理MainActivity除直接显示，监听外的一切业务逻辑
 * 连接层 :
 * Presenter -> cn.heyan.bililite.presenters.activity.MainPresenter
 * View -> cn.heyan.bililite.view.activity.MainActivity
 * Created by HeYan on 2018/2/11 0011.
 */

class MainPresenter(mainView:MainActivity){

    /**
     * View层对象
     */
    private val mainActivity = mainView

    /**
     * Model层对象
     */
    private val mainModel: MainModel = MainModel(this)

    private val gridAdapter by lazy {
        NavAdapter(mainModel.grid.data,mainActivity)
    }


    fun onViewStart(){

        mainActivity.setGridAdapter(gridAdapter)

    }

    fun onGridItemClick(adapterView: AdapterView<*>,view:View,position:Int,id:Long){
        mainModel.grid.newChose(position)
        gridAdapter.notifyDataSetChanged()
    }


    fun setToolbarTitle(title:String){
        mainActivity.setTitle(title)
    }




}

