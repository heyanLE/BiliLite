package cn.heyan.bililite.view.activities.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import cn.heyan.bililite.R
import cn.heyan.bililite.presenters.activities.main.MainPresenter
import kotlinx.android.synthetic.main.activity_main_app_bar.*
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.activity_main_main_content.*

/**
 * MainActivity
 * Main组件的View层
 * 连接层:
 * Presenters -> cn.heyan.bililite.presenters.activity.main
 * Created by HeYan on 2018/2/11 0011.
 */

class MainActivity:AppCompatActivity() , ViewPager.OnPageChangeListener
        , TabLayout.OnTabSelectedListener{

    /**
     * 通过懒加载
     * 实例化Presenter
     * （然后Presenter会实例化Model层）
     */
    private val mainPresenter by lazy {
        MainPresenter(this)
    }


    /**
     * 各种控件获取
     */
    private val drawerLayout:DrawerLayout by lazy {
        activity_main_drawer_layout
    }
    private val viewPager:ViewPager by lazy {
        activity_main_pager_view
    }
    private val tabLayout:TabLayout by lazy {
        activity_main_tab_layout
    }
    private val gridView:GridView by lazy {
        activity_main_grid_view
    }
    private val linearTheme:LinearLayout by lazy {
        activity_main_theme_linear_layout
    }
    private val linearDelete:LinearLayout by lazy {
        activity_main_delete_linear_layout
    }
    private val linearSetting:LinearLayout by lazy {
        activity_main_setting_linear_layout
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, activity_main_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        //设置ViewPager tabLayout监听
        viewPager.addOnPageChangeListener(this)
        tabLayout.addOnTabSelectedListener(this)

        gridView.setOnItemClickListener { adapterView, view, i, l ->
            mainPresenter.onGridItemClick(adapterView,view,i,l)
        }

    }

    /**
     * 当OnStart时候
     * 发信息给P层
     * 让他更新数据（此时View已经绘制好）
     */
    override fun onStart() {
        super.onStart()
        mainPresenter.onViewStart()
    }


    //===============================================
    //presenter层调用的方法
    fun setViewPagerAdapter(pagerAdapter: PagerAdapter){
        viewPager.adapter = pagerAdapter
    }

    fun setGridAdapter(listAdapter: ListAdapter){
        gridView.adapter = listAdapter
    }

    fun setTitle(title:String){
        supportActionBar?.title = title
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    //返回键监听
    override fun onBackPressed() {
        //如果侧滑栏开着，关闭，如果关着，不操作
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    //===================================
    //tabLayout监听重写
    override fun onTabSelected(tab: TabLayout.Tab) {
        //TabLayout里的TabItem被选中的时候触发
        //同步选择相应的ViewPager
        viewPager.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    //======================================
    //viewPager监听重写
    override fun onPageSelected(position: Int) {
        //当滑动到相应的ViewPager
        //TabLayout同步选择
        tabLayout.getTabAt(position)!!.select()
        //HeLog.i("tab -> ${position}",this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}

