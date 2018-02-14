package cn.heyan.bililite.presenters.main.activities

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.AdapterView
import cn.heyan.bililite.model.activity.MainModel
import cn.heyan.bililite.presenters.main.adapter.NavAdapter
import cn.heyan.bililite.view.main.activity.MainActivityInt
import cn.heyan.bililite.view.main.fragment.MainFragment

/**
 * MainActivityPresenter
 * Created by HeYan on 2018/2/14 0014.
 */

class MainActivityPresenter(mainActivityView: MainActivityInt):MainActivityPresenterInt{

    //view层对象
    private val mainView = mainActivityView

    private val navAdapter = NavAdapter(mainView.getContext(),0)

    private var nowTid = 0

    override fun onGridItemOnClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        navAdapter.newCho(i)
        mainView.setToolbarTitle(navAdapter.title)

        var mainFragment:Fragment? = mainView.findFragmentByTag(l.toInt().toString())
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(l.toInt())
            mainView.addFragment(mainFragment,l.toInt().toString())
            //fragmentList.add(mainFragment)
        }else{

            mainView.showFragment(mainFragment,nowTid)
        }

        nowTid = l.toInt()

    }

    override fun onViewCreate() {
        mainView.setGridAdapter(navAdapter)
        mainView.setToolbarTitle(navAdapter.title)

        nowTid = navAdapter.tid

        var mainFragment:Fragment? = mainView.findFragmentByTag(navAdapter.tid.toString())
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(navAdapter.tid)
            mainView.addFragment(mainFragment,navAdapter.tid.toString())
            //fragmentList.add(mainFragment)
        }else{
            mainView.showFragment(mainFragment)
        }



    }

    override fun choOne() {
        navAdapter.newCho(0)
        mainView.setToolbarTitle(navAdapter.title)
        var mainFragment:Fragment? = mainView.findFragmentByTag(navAdapter.tid.toString())
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(navAdapter.tid)
            mainView.addFragment(mainFragment,navAdapter.tid.toString())
            //fragmentList.add(mainFragment)
        }else{
            mainView.showFragment(mainFragment,nowTid)
        }
        nowTid = navAdapter.dataList[0].tid
    }

    override fun onViewRecreate() {
        //mainView.removeFragment(fragmentList)
        //navAdapter.newCho(0)
        //mainView.setToolbarTitle(navAdapter.title)
/*
        navAdapter.newCho(navAdapter.tid,true)
        mainView.setToolbarTitle(navAdapter.title)

        var mainFragment:Fragment? = mainView.findFragmentByTag(navAdapter.tid.toString())
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(navAdapter.tid)
            mainView.addFragment(mainFragment,navAdapter.tid.toString())
            //fragmentList.add(mainFragment)
        }else{
            mainView.showFragment(mainFragment)
        }
*/
    }


    override fun viewRecreate() {

        navAdapter.newCho(0)
        mainView.setToolbarTitle(navAdapter.title)
        var mainFragment:Fragment? = mainView.findFragmentByTag(navAdapter.tid.toString())
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance(navAdapter.tid)
            mainView.addFragmentSta(mainFragment,navAdapter.tid.toString())
            //fragmentList.add(mainFragment)
        }else{
            mainView.showFragmentSta(mainFragment,nowTid)
        }
        nowTid = navAdapter.dataList[0].tid

    }

}
