package cn.heyan.bililite.view.main.fragment


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter

/**
 * MainFragmentInt
 * Created by HeYan on 2018/2/14 0014.
 */

interface MainFragmentInt{

    fun setPagerAdapter(pagerAdapter: PagerAdapter)
    fun getTheChildFragmentManager(): FragmentManager
    fun getTheTid():Int

}
