package cn.heyan.bililite.presenters.main.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.app.ThemeMonitor
import cn.heyan.bililite.model.activity.MainModel
import cn.heyan.bililite.model.utils.BiliHttp.BiliApi
import cn.heyan.bililite.model.utils.BiliHttp.HttpGet
import cn.heyan.bililite.model.utils.BiliHttp.VideoData
import cn.heyan.bililite.model.utils.HeLog
import cn.heyan.bililite.presenters.main.adapter.PartListAdapter
import cn.heyan.bililite.view.fragments.LazyLoadFragment
import cn.heyan.bililite.view.main.fragment.MainFragment
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * PartListFragment
 * Created by HeYan on 2018/2/14 0014.
 */

class PartListFragment():LazyLoadFragment(){

    override val LAYOUT_ID: Int
        get() = R.layout.fragment_part_list_layout

    val title by lazy {
        arguments!!.getString("title")
    }

    val tid by lazy {
        arguments!!.getInt("tid")
    }

    private val recy by lazy {
        rootView!!.findViewById<RecyclerView>(R.id.fragment_main_recycler)
    }

    private val refreshLayout by lazy {
        rootView!!.findViewById<SwipeRefreshLayout>(R.id.fragment_main_swip)
    }

    private val linearLayoutNul by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_linear_nul)
    }

    private val textViewTime by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_tv_time)
    }

    private val inearSort by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_linear_sort)
    }

    private val textSort by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_tv_sort)
    }

    private val linearSortCho by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_top_lv_sort)
    }

    val adapter by lazy {
        PartListAdapter(context as Context)
    }

    val textViewSortNew by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_new)
    }

    val textViewSortPlay by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_play)
    }

    val textViewSortScores by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_scores)
    }

    val textViewSortStow by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_stow)
    }

    val textViewSortDm by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_dm)
    }

    val textViewSortCoin by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_top_tv_sort_coin)
    }



    var nowSort = "time"
    var nowPage = 0

    var timeBegin = MainModel.getTimeBeginShow()
    var timeEnd = MainModel.getTimeEndShow()

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
         * @param title 参数title 分区名字
         * @return 含参的MainFragment对象
         */
        fun newInstance(tid:Int,title:String): PartListFragment {
            val mainFragment = PartListFragment()
            val bundle = Bundle()
            bundle.putInt("tid",tid)
            bundle.putString("title",title)
            mainFragment.arguments = bundle
            return mainFragment

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return rootView
    }

    private fun reFresh(){

        refreshLayout.isRefreshing = true
        Handler().post({

            nowPage = 1
            if (nowSort == "time"){
                MainModel.getNetWortData(nowPage.toString(),tid)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            adapter.sort = nowSort
                            adapter.setNewData(it)
                            linearLayoutNul.visibility = View.INVISIBLE
                            refreshLayout.isRefreshing = false
                        },{
                            adapter.sort = nowSort
                            val list:MutableList<VideoData> = mutableListOf()
                            adapter.setNewData(list)
                            linearLayoutNul.visibility = View.VISIBLE
                            //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                            refreshLayout.isRefreshing = false
                        })
            }else{
                MainModel.getNetWortData(nowPage.toString(),tid,nowSort)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            adapter.sort = nowSort
                            adapter.setNewData(it)
                            linearLayoutNul.visibility = View.INVISIBLE
                            refreshLayout.isRefreshing = false
                        },{
                            val list:MutableList<VideoData> = mutableListOf()
                            adapter.setNewData(list)
                            linearLayoutNul.visibility = View.VISIBLE
                            //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                            refreshLayout.isRefreshing = false
                        })
            }

        })
    }

    fun newCho(sort:String){

        var color = 0

        when(ThemeMonitor.nowThemeId){
            "胖次蓝" -> color = R.color.themeBlue
            "少女粉" -> color = R.color.themePink
            "原谅绿" -> color = R.color.themeGreen
            "咸蛋黄" -> color = R.color.themeYellow
            "姨妈红" -> color = R.color.themeRed
            "基佬紫" -> color = R.color.themePurple
            "小灰灰" -> color = R.color.themeGray
            "寡妇黑" -> color = R.color.themeBlack
        }

        when(nowSort){
            "time" -> textViewSortNew.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
            "click" -> textViewSortPlay.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
            "scores" -> textViewSortScores.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
            "dm" -> textViewSortDm.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
            "stow" -> textViewSortStow.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
            "coin" -> textViewSortCoin.setTextColor(ContextCompat.getColor(context as Context, R.color.nothing))
        }

        when(sort){
            "time" -> textViewSortNew.setTextColor(ContextCompat.getColor(context as Context, color))
            "click" -> textViewSortPlay.setTextColor(ContextCompat.getColor(context as Context, color))
            "scores" -> textViewSortScores.setTextColor(ContextCompat.getColor(context as Context, color))
            "dm" -> textViewSortDm.setTextColor(ContextCompat.getColor(context as Context, color))
            "stow" -> textViewSortStow.setTextColor(ContextCompat.getColor(context as Context, color))
            "coin" -> textViewSortCoin.setTextColor(ContextCompat.getColor(context as Context, color))
        }

        linearSortCho.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        linearSortCho.visibility = LinearLayout.GONE
                    }
                })

        nowSort = sort
        reFresh()

    }

    override fun onFirstVisibility() {

        val timeS = MainModel.getTimeBeginShow() + "至" +MainModel.getTimeEndShow()
        textViewTime.text = timeS

        var color = 0

        when(ThemeMonitor.nowThemeId){
            "胖次蓝" -> color = R.color.themeBlue
            "少女粉" -> color = R.color.themePink
            "原谅绿" -> color = R.color.themeGreen
            "咸蛋黄" -> color = R.color.themeYellow
            "姨妈红" -> color = R.color.themeRed
            "基佬紫" -> color = R.color.themePurple
            "小灰灰" -> color = R.color.themeGray
            "寡妇黑" -> color = R.color.themeBlack
        }

        when(nowSort){
            "time" -> textViewSortNew.setTextColor(ContextCompat.getColor(context as Context, color))
            "click" -> textViewSortPlay.setTextColor(ContextCompat.getColor(context as Context, color))
            "scores" -> textViewSortScores.setTextColor(ContextCompat.getColor(context as Context, color))
            "dm" -> textViewSortDm.setTextColor(ContextCompat.getColor(context as Context, color))
            "stow" -> textViewSortStow.setTextColor(ContextCompat.getColor(context as Context, color))
            "coin" -> textViewSortCoin.setTextColor(ContextCompat.getColor(context as Context, color))
        }

        textViewSortNew.setOnClickListener {
            if (nowSort != "time") newCho("time")
            textSort.text = "最新"
        }

        textViewSortPlay.setOnClickListener {
            if (nowSort != "click") newCho("click")
            textSort.text = "播放"
        }

        textViewSortScores.setOnClickListener {
            if (nowSort != "scores") newCho("scores")
            textSort.text = "评论"
        }

        textViewSortDm.setOnClickListener {
            if (nowSort != "dm") newCho("dm")
            textSort.text = "弹幕"
        }

        textViewSortStow.setOnClickListener {
            if (nowSort != "stow") newCho("stow")
            textSort.text = "收藏"
        }

        textViewSortCoin.setOnClickListener {
            if (nowSort != "coin") newCho("coin")
            textSort.text = "硬币"
        }

        adapter.bindToRecyclerView(recy)
        adapter.setEnableLoadMore(true)
        adapter.disableLoadMoreIfNotFullPage()
        adapter.openLoadAnimation()

        recy.adapter = adapter
        recy.layoutManager = LinearLayoutManager(context)


        refreshLayout.setSize(SwipeRefreshLayout.LARGE)
        refreshLayout.tag = 158
        when(ThemeMonitor.nowThemeId){
            "胖次蓝" -> refreshLayout.setColorSchemeColors(0x2094f1)
            "少女粉" -> refreshLayout.setColorSchemeColors(0xf97197)
            "原谅绿" -> refreshLayout.setColorSchemeColors(0x89c14a)
            "咸蛋黄" -> refreshLayout.setColorSchemeColors(0xfcbf07)
            "姨妈红" -> refreshLayout.setColorSchemeColors(0xf24235)
            "基佬紫" -> refreshLayout.setColorSchemeColors(0x9a27af)
            "小灰灰" -> refreshLayout.setColorSchemeColors(0x607d8a)
            "寡妇黑" -> refreshLayout.setColorSchemeColors(0x000000)
        }
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.white)
        refreshLayout.setOnRefreshListener {
            reFresh()
        }

        inearSort.setOnClickListener {
            if (linearSortCho.visibility == LinearLayout.VISIBLE) {
                linearSortCho.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                linearSortCho.visibility = LinearLayout.GONE
                            }
                        })
            }
            else {
                linearSortCho.alpha = 0f
                linearSortCho.visibility = LinearLayout.VISIBLE
                linearSortCho.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null)
            }
        }

        rootView!!.findViewById<View>(R.id.fragment_main_view_top_non)
                .setOnClickListener {
                    linearSortCho.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    linearSortCho.visibility = LinearLayout.GONE
                                }
                            })
                }

        adapter.setOnLoadMoreListener({
            Handler().post({

                HeLog.i("loadMore",this)

                val pp = nowPage + 1
                if (nowSort == "time") {
                    MainModel.getNetWortData(pp.toString(), tid)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                recy.post({
                                    adapter.addData(it)
                                    if (it[0].nowP == it[0].pageN) {
                                        nowPage = it[0].nowP
                                        adapter.loadMoreEnd()
                                    } else {
                                        nowPage = it[0].nowP
                                        adapter.loadMoreComplete()
                                    }
                                    refreshLayout.isRefreshing = false
                                    linearLayoutNul.visibility = View.INVISIBLE
                                })
                            }, {
                                adapter.loadMoreFail()
                                val list: MutableList<VideoData> = mutableListOf()
                                adapter.setNewData(list)
                                linearLayoutNul.visibility = View.VISIBLE
                                refreshLayout.isRefreshing = false
                            })
                }else{
                    MainModel.getNetWortData(pp.toString(),tid,nowSort)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                recy.post({
                                    adapter.addData(it)
                                    if (it[0].nowP == it[0].pageN) {
                                        nowPage = it[0].nowP
                                        adapter.loadMoreEnd()
                                    } else {
                                        nowPage = it[0].nowP
                                        adapter.loadMoreComplete()
                                    }
                                    refreshLayout.isRefreshing = false
                                    linearLayoutNul.visibility = View.INVISIBLE
                                })
                            }, {
                                adapter.loadMoreFail()
                                val list: MutableList<VideoData> = mutableListOf()
                                adapter.setNewData(list)
                                linearLayoutNul.visibility = View.VISIBLE
                                refreshLayout.isRefreshing = false
                            })
                }


            })
        },recy)

        reFresh()

    }

    override fun onInVisibility() {

    }

    override fun onVisibility() {
        val timeS = MainModel.getTimeBeginShow() + "至" +MainModel.getTimeEndShow()
        textViewTime.text = timeS
        if (refreshLayout.tag != 158){
            onFirstVisibility()
        }
        if (timeBegin != MainModel.getTimeBeginShow() || timeEnd != MainModel.getTimeEndShow()){
            HeLog.i("timeBegin",timeBegin,this)
            HeLog.i("MainModel.getTimeBegin()",MainModel.getTimeBegin(),this)
            HeLog.i("timeEnd",timeEnd,this)
            HeLog.i("MainModel.getTimeEnd()",MainModel.getTimeEnd(),this)
            timeBegin = MainModel.getTimeBegin()
            timeEnd = MainModel.getTimeEnd()
            reFresh()
        }
    }

}
