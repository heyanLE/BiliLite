package cn.heyan.bililite.view.main.activity

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.BaseAdapter

/**
 * MainActivityInt
 * Main组件里Activity的View层接口
 * Created by HeYan on 2018/2/14 0014.
 */

interface MainActivityInt {

    fun setToolbarTitle(title:String)
    fun addFragment(fragment: Fragment,tag:String)
    fun showFragment(fragment: Fragment,tid:Int)
    fun showFragment(fragment: Fragment)
    fun addFragmentSta(fragment: Fragment,tag:String)
    fun showFragmentSta(fragment: Fragment,tid:Int)
    fun showFragmentSta(fragment: Fragment)
    fun setGridAdapter(baseAdapter: BaseAdapter)
    fun findFragmentByTag(tag:String):Fragment?

    fun getContext(): Context



}
