package cn.heyan.bililite.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.heyan.bililite.R
import cn.heyan.bililite.adapters.NavAdapter
import cn.heyan.bililite.adapters.PartFragAdapter
import cn.heyan.bililite.app.ThemeMonitor
import cn.heyan.bililite.fragment.MainFragment
import cn.heyan.bililite.utils.HeLog
import cn.heyan.bililite.utils.appdata.DataJson
import cn.heyan.bililite.utils.appdata.DataManager
import cn.heyan.bililite.utils.bilidata.BiliData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * MainActivity
 * Created by eke_l on 2018/1/27 0027.
 */

class MainActivity : AppCompatActivity() ,ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    var nowPart = "番剧"


    private val navAdapter:NavAdapter by lazy {
        NavAdapter(this)
    }

    private val fragAdapter:PartFragAdapter by lazy {
        PartFragAdapter(supportFragmentManager,nowPart)
    }

    fun reFreshPage(){
        val list = (BiliData.part.part)[nowPart]!!.children
        val listF:MutableList<MainFragment> = mutableListOf()
        val listT:MutableList<String> = mutableListOf()
        for(map in list){
            val frag = MainFragment.newInstance(map["tid"] as String)
            listF.add(frag)
            listT.add(map["name"] as String)
        }
        fragAdapter.reSet(listF,listT)
        main_tab.getTabAt(0)!!.select()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //nav_view.setNavigationItemSelectedListener(this)

        //nav_view.setItemTextColor(null)
        //nav_view.setItemIconTintList(null)

        nav_view.itemTextColor = null
        nav_view.itemIconTintList = null

        main_nav_grid.adapter = navAdapter
        main_nav_grid.setOnItemClickListener { _, _, position, _ ->
            navAdapter.onItemOnClick(position)
            navAdapter.notifyDataSetChanged()
            drawer_layout.closeDrawer(GravityCompat.START)
            //reFreshPage()
        }

        main_tab.setTabTextColors(ContextCompat.getColor(this, R.color.font),
                ContextCompat.getColor(this, R.color.white))
        main_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(this,  R.color.white))
        val f = 10.0f
        ViewCompat.setElevation(main_tab,f)
        main_tab.setupWithViewPager(main_vp)
        main_tab.tabMode = TabLayout.MODE_SCROLLABLE

        supportActionBar!!.title = nowPart

        main_nav_linear_theme.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,ThemeActivity::class.java)
            intent.putExtra(ThemeMonitor.THEME_EXTRA_KEY,DataJson.data.style)
            startActivity(intent)
        }

        main_vp.adapter = fragAdapter





    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onTabSelected(tab: TabLayout.Tab) {
        //TabLayout里的TabItem被选中的时候触发
        main_vp.currentItem = tab.position
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onPageSelected(position: Int) {
        main_tab.getTabAt(position)!!.select()
        HeLog.i("tab -> ${position}",this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }


}
