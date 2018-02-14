package cn.heyan.bililite.view.main.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.presenters.main.fragments.MainFragmentPresenter
import cn.heyan.bililite.presenters.main.fragments.MainFragmentPresenterInt

/**
 * MainFragment
 * Created by HeYan on 2018/2/14 0014.
 */

class MainFragment:Fragment(),MainFragmentInt,ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    //懒加载tid
    private val tid by lazy {
        arguments!!.getInt("tid")
    }

    private var rootView:View? = null

    private val presenter:MainFragmentPresenterInt by lazy {
        MainFragmentPresenter(this)
    }

    //各种view获取
    private val tabLayout by lazy {
        rootView!!.findViewById<TabLayout>(R.id.fragment_main_tab_layout)
    }
    private val viewPager by lazy {
        rootView!!.findViewById<ViewPager>(R.id.fragment_main_pager_view)
    }

    companion object {

        /**
         * 获取一个新的实例
         * 因为如果fragment重启的时候
         * 系统调用的是无参的Fragment构造方法
         * 所以不能通过构造方法传参
         * 通过实例化一个本身
         * 设置arguments为bundle
         * 然后返回这个对象来创建实例并达到传参效果
         * @param tid 参数Tid 分区id
         * @return 含参的MainFragment对象
         */
        fun newInstance(tid:Int):MainFragment{
            val mainFragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("tid",tid)
            mainFragment.arguments = bundle
            return mainFragment

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_main_layout,container,false)
        val f = 10.0f
        ViewCompat.setElevation(tabLayout,f)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        presenter.onCreateView()
        viewPager.offscreenPageLimit = 8
        //rootView!!.findViewById<TextView>(R.id.fragment_main_tv_test).text = tag
        return rootView
    }



    override fun setPagerAdapter(pagerAdapter: PagerAdapter) {
        viewPager.adapter = pagerAdapter
    }

    override fun getTheChildFragmentManager(): FragmentManager {
        return childFragmentManager
    }

    override fun getTheTid(): Int {
        return tid
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        //TabLayout里的TabItem被选中的时候触发
        viewPager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onPageSelected(position: Int) {
        tabLayout.getTabAt(position)!!.select()
        //HeLog.i("tab -> ${position}",this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}
