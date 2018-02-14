package cn.heyan.bililite.view.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.LinearLayout
import cn.heyan.bililite.R
import cn.heyan.bililite.app.ThemeMonitor
import cn.heyan.bililite.presenters.main.activities.MainActivityPresenter
import cn.heyan.bililite.presenters.main.activities.MainActivityPresenterInt
import cn.heyan.bililite.view.activities.ThemeActivity
import kotlinx.android.synthetic.main.activity_main_app_bar.*
import kotlinx.android.synthetic.main.activity_main_layout.*

/**
 * MainActivity
 * MainView里的activity
 * Created by HeYan on 2018/2/14 0014.
 */

class MainActivity:AppCompatActivity(), MainActivityInt {

    /**
     * 懒加载
     * 实例化一个presenter对象
     */
    private val presenter: MainActivityPresenterInt by lazy {
        MainActivityPresenter(this)
    }

    //是否recreate标识
    private var isRecreate = false

    /**
     * 各种控件获取
     * 通过懒加载
     */
    private val drawerLayout: DrawerLayout by lazy {
        activity_main_drawer_layout
    }
    private val gridView: GridView by lazy {
        activity_main_grid_view
    }
    private val linearTheme: LinearLayout by lazy {
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

        //如果为recreate后的onCreate
        //还原recreate标识
        //回调方法
        if (isRecreate){
            return
        }

        //设置布局文件
        setContentView(R.layout.activity_main_layout)

        //修改Toolbar
        setSupportActionBar(toolbar)

        //侧滑栏交互
        val toggle = ActionBarDrawerToggle(
                this, activity_main_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //向P层回调
        presenter.onViewCreate()

        //设置GridView监听
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
            presenter.onGridItemOnClick(adapterView,view,i,l)
        }

        linearTheme.setOnClickListener {
            presenter.choOne()
            val intent = Intent()
            intent.setClass(this, ThemeActivity::class.java)
            intent.putExtra(ThemeMonitor.THEME_EXTRA_KEY,ThemeMonitor.nowThemeId)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isRecreate){
            isRecreate = false
            presenter.onViewRecreate()
            return
        }
    }

    override fun recreate() {
        isRecreate = true
        presenter.viewRecreate()
        super.recreate()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()

    }

    //======================================================================================
    //接口回调：让P层回调的

    /**
     * 添加frameLayout的fragment
     * 通过TAG标记
     * @param fragment 要设置的fragment
     */
    override fun addFragment(fragment: Fragment,tag:String) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        art.add(R.id.activity_main_frame_layout,fragment,tag)
        art.commit()
    }

    override fun addFragmentSta(fragment: Fragment, tag: String) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        art.add(R.id.activity_main_frame_layout,fragment,tag)
        art.commitAllowingStateLoss()
    }

    /**
     * 显示Fragment
     * @param fragment 要显示的fragment
     */
    override fun showFragment(fragment: Fragment,tid:Int) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        art.hide(fm.findFragmentByTag(tid.toString()))
        art.show(fragment)
        art.commit()
    }

    override fun showFragmentSta(fragment: Fragment, tid: Int) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        art.hide(fm.findFragmentByTag(tid.toString()))
        art.show(fragment)
        art.commitAllowingStateLoss()
    }

    override fun showFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        //art.hide(fm.findFragmentByTag(tid.toString()))
        art.show(fragment)
        art.commit()
    }

    override fun showFragmentSta(fragment: Fragment) {
        val fm = supportFragmentManager
        val art = fm.beginTransaction()
        //art.hide(fm.findFragmentByTag(tid.toString()))
        art.show(fragment)
        art.commitAllowingStateLoss()
    }

    /**
     * 通过TAG标记寻找fragment
     * @param tag TAG标记
     * @return fragment对象
     */
    override fun findFragmentByTag(tag: String): Fragment? {
        val fm = supportFragmentManager
        return fm.findFragmentByTag(tag)
    }

    /**
     * 设置ToolBar的Title
     * @param title 要设置的Title
     */
    override fun setToolbarTitle(title:String) {
        supportActionBar!!.title = title
    }

    /**
     * 设置GridView的Adapter
     * @param baseAdapter 要设置的Adapter
     */
    override fun setGridAdapter(baseAdapter: BaseAdapter) {
        gridView.adapter = baseAdapter
    }

    /**
     * 获取上下文
     * @return 上下文
     */
    override fun getContext(): Context {
        return this
    }

}
