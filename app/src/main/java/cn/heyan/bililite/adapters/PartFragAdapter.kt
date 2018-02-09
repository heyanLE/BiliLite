package cn.heyan.bililite.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import cn.heyan.bililite.fragment.MainFragment
import cn.heyan.bililite.utils.bilidata.BiliData

/**
 * PartFragAdapter
 * Created by HeYan on 2018/2/7 0007.
 */

class PartFragAdapter(fm:FragmentManager,nowPart:String): FragmentPagerAdapter(fm){

    var fragmentList:MutableList<MainFragment> = mutableListOf()

    var pageTitle:MutableList<String?> = mutableListOf()

    val fm = fm

    init {
        val list = (BiliData.part.part)[nowPart]!!.children
        fragmentList.clear()
        pageTitle.clear()
        for (map in list){
            fragmentList.add(MainFragment.newInstance(map["tid"] as String))
            pageTitle.add(map["name"] as String)
        }
        //fragmentList[0].isVisibility = true
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return pageTitle.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitle[position]
    }

    override fun getItemPosition(obj: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun reSet(list: MutableList<MainFragment>,listT: MutableList<String>){
        val tra = fm.beginTransaction()
        for (fragment in fragmentList){
            tra.remove(fragment)
        }
        tra.commit()
        fm.executePendingTransactions()
        fragmentList.clear()
        fragmentList.addAll(list)
        pageTitle.clear()
        pageTitle.addAll(listT)
        notifyDataSetChanged()
    }

}
