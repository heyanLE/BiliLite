package cn.heyan.bililite.presenters.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.heyan.bililite.model.activity.MainModel
import cn.heyan.bililite.presenters.main.fragments.PartListFragment

/**
 * HePagerAdapter
 * Created by HeYan on 2018/2/14 0014.
 */

class HePagerAdapter(fm:FragmentManager, tid:Int):FragmentPagerAdapter(fm){

    val fragmentList:MutableList<PartListFragment> = mutableListOf()

    init {
        val list = MainModel.getPartDataList(tid)
        for (data in list){
            val fragment:PartListFragment = PartListFragment.newInstance(data.tid,data.title)
            fragmentList.add(fragment)
        }
    }



    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].title
    }

}

class PagerData{

    var tid = 0
    var title = ""

}
