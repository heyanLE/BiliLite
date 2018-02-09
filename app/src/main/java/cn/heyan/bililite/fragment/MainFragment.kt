package cn.heyan.bililite.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import cn.heyan.bililite.R
import cn.heyan.bililite.adapters.PartListAdapter
import cn.heyan.bililite.app.ThemeMonitor
import cn.heyan.bililite.netword.BiliApi
import cn.heyan.bililite.netword.HttpData
import cn.heyan.bililite.netword.HttpGet
import cn.heyan.bililite.netword.VideoData
import com.ajguan.library.EasyRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.LoadMoreView
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * MainFragment
 * Created by HeYan on 2018/2/9 0009.
 */

class MainFragment:BaseLazyLoadFragment(){

    //布局文件id(必须设置)
    override val LAYOUT_ID: Int = R.layout.fragment_main

    //懒加载tid
    val tid : String by lazy {
        arguments!!.getString("tid")
    }

    var ifReFresh = false

    //页数
    var page = 0

    //各种View初始化
    val recyclerView: RecyclerView by lazy {
        rootView!!.findViewById<RecyclerView>(R.id.fragment_main_recycler)
    }
    val refreshLayout: SwipeRefreshLayout by lazy {
        rootView!!.findViewById<SwipeRefreshLayout>(R.id.fragment_main_swip)
    }
    val linearLayoutSort:LinearLayout by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_top_lv_sort)
    }
    val linearLayoutSortButton:LinearLayout by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_linear_sort)
    }
    val textViewTime:TextView by lazy {
        rootView!!.findViewById<TextView>(R.id.fragment_main_tv_time)
    }
    val linearLayoutNul:LinearLayout by lazy {
        rootView!!.findViewById<LinearLayout>(R.id.fragment_main_linear_nul)
    }

    //adapter初始化
    val adapter:PartListAdapter by lazy {
        PartListAdapter(activity as Context,R.layout.item_part_list,recyclerView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
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
        fun newInstance(tid:String):MainFragment{
            val mainFragment = MainFragment()
            val bundle = Bundle()
            bundle.putString("tid",tid)
            mainFragment.arguments = bundle
            return mainFragment

        }

    }





    fun reFresh(){


        refreshLayout.isRefreshing = true
        Handler().post({

            page = 1
            val url = BiliApi.getPartListNew(page.toString(),tid)
            HttpGet.getData(url,HttpData.TYPE_NEW).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        recyclerView.post({
                            adapter.setNewData(it)
                            refreshLayout.isRefreshing = false
                            linearLayoutNul.visibility = View.INVISIBLE
                            //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                        })
                    },{
                        val list:MutableList<VideoData> = mutableListOf()
                        adapter.setNewData(list)
                        linearLayoutNul.visibility = View.VISIBLE
                        //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                        refreshLayout.isRefreshing = false
                    })


        })
    }


    /**
     * 当第一次可见时回调
     */
    override fun onFirstVisible(){

        //adapter.bindToRecyclerView(recyclerView)

        //adapter.isUseEmpty(true)
        //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView)

        adapter.setEnableLoadMore(true)

        refreshLayout.setSize(SwipeRefreshLayout.LARGE)
        when(ThemeMonitor.theme){
            "blue" -> refreshLayout.setColorSchemeColors(0x2094f1)
            "pink" -> refreshLayout.setColorSchemeColors(0xf97197)
            "green" -> refreshLayout.setColorSchemeColors(0x89c14a)
            "yellow" -> refreshLayout.setColorSchemeColors(0xfcbf07)
            "red" -> refreshLayout.setColorSchemeColors(0xf24235)
            "purple" -> refreshLayout.setColorSchemeColors(0x9a27af)
        }
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.white)
        refreshLayout.setOnRefreshListener {
            reFresh()
        }

        reFresh()

        adapter.setOnLoadMoreListener({

            refreshLayout.isRefreshing = true
            Handler().post({

                val pp = page + 1
                val url = BiliApi.getPartListNew(pp.toString(),tid)
                HttpGet.getData(url,HttpData.TYPE_NEW).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            recyclerView.post({
                                adapter.addData(it)
                                if (it[0].nowP == it[0].pageN){
                                    page = it[0].nowP
                                    adapter.loadMoreEnd()
                                }
                                else {
                                    page = it[0].nowP
                                    adapter.loadMoreComplete()
                                }
                                refreshLayout.isRefreshing = false
                                //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                            })
                        },{
                            /**
                            val list:MutableList<VideoData> = mutableListOf()
                            adapter.setNewData(list)
                            linearLayoutNul.visibility = View.VISIBLE
                            //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                            **/
                            adapter.loadMoreFail()
                            val list:MutableList<VideoData> = mutableListOf()
                            adapter.setNewData(list)
                            linearLayoutNul.visibility = View.VISIBLE
                            //adapter.setEmptyView(R.layout.item_part_list_empty,recyclerView.parent as ViewGroup)
                            refreshLayout.isRefreshing = false
                        })


            })

        },recyclerView)



        /**
        adapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{

            override fun onLoadMoreRequested() {
                Handler().postDelayed({

                    page ++
                    val url = BiliApi.getPartListNew(page.toString(),tid)
                    HttpGet.getData(url,HttpData.TYPE_NEW).subscribe{

                        recyclerView.post({
                            adapter.data.addAll(it)
                            adapter.notifyDataSetChanged()
                        })

                    }


                },500)
            }

        },recyclerView)**/

/**
        val url = BiliApi.getPartListNew(page.toString(),tid)
        HttpGet.getData(url,HttpData.TYPE_NEW).subscribe {
            recyclerView.postDelayed({

                recyclerView.post({
                    adapter.data.addAll(it)
                    adapter.notifyDataSetChanged()
                })


            },500)
        }
        **/
/**
        easyRefreshLayout.addEasyEvent(object : EasyRefreshLayout.EasyEvent{
            override fun onLoadMore() {
                Handler().postDelayed({

                    page ++
                    val url = BiliApi.getPartListNew(page.toString(),tid)
                    HttpGet.getData(url,HttpData.TYPE_NEW).subscribe{

                        recyclerView.post({
                            easyRefreshLayout.loadMoreComplete()
                            adapter.data.addAll(it)
                            adapter.notifyDataSetChanged()
                        })

                    }


                },500)
            }

            override fun onRefreshing() {

                Handler().postDelayed({

                    page = 1
                    val url = BiliApi.getPartListNew(page.toString(),tid)
                    HttpGet.getData(url,HttpData.TYPE_NEW).subscribe{

                        recyclerView.post({
                            adapter.setNewData(it)
                            adapter.notifyDataSetChanged()

                            easyRefreshLayout.refreshComplete()
                        })

                    }


                },500)

            }
        })
        **/
    }

    /**
     * 当不是第一次可见时回调
     */
    override fun onVisible(){
    }

    /**
     * 当不可见时回调
     */
    override fun onInVisible(){}
}


