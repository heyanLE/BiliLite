package cn.heyan.bililite.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.heyan.bililite.utils.HeLog

/**
 * BaseLazyLoadFragment
 * Created by HeYan on 2018/2/9 0009.
 */

abstract class BaseLazyLoadFragment:Fragment(){

    /**
     * 懒加载Fragment
     * 因为PageView里有预加载机制
     * 直接用会导致不必要的操作影响效率
     * 所以需要懒加载
     */

    //是否被实例化
    var isInitializer = false
    //是否可见
    var isVisibility = false
    //是否第一次可见
    var isFirstShow = true

    //布局ID
    abstract val LAYOUT_ID:Int

    var rootView:View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //已经被实例化
        isInitializer = true

        rootView = inflater.inflate(LAYOUT_ID,container,false)

        isVisibility = userVisibleHint

        HeLog.i("isFirstShow -> ${isFirstShow}",this)
        HeLog.i("isVisibility -> ${isVisibility}",this)

        //如果是第一次可见
        //回调onFirstVisible
        //ifFirstShow标示为false


        if(isFirstShow && isVisibility){
            isFirstShow = false
            HeLog.i("onFragmentFirst",this)
            onFirstVisible()
        }
        return rootView
    }

    /**
     * 当Fragment可见性改变时候回调
     * 因为可能在OnCreateView前回调 所以需要实例化标示
     * isInitializer
     * @param isVisibleToUser 是否可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        //RootView还未创建 return
        if (rootView == null){
            return
        }

        //如果可见 且 View已经创建 且 Fragment已经实例化
        if (isVisibleToUser && isInitializer){
            //可见标示为true
            isVisibility = true


            HeLog.i("isFirstShow -> ${isFirstShow}",this)
            HeLog.i("isVisibility -> ${isVisibility}",this)
            //如果是第一次可见
            //回调 onFirstVisible
            if (isFirstShow){
                isFirstShow = false
                onFirstVisible()
            }
            //否则 回调OnVisible
            else{
                onVisible()
            }
        }

        //如果不可见 且 View已经创建 且 Fragment已经实例化
        if(! isVisibleToUser && isInitializer){
            //可见标示为false
            isVisibility = false

            //回调OnInVisible
            onInVisible()
        }

    }
    /**
     * 在Fragment销毁时回调
     * 重置
     */
    override fun onDestroy() {
        super.onDestroy()
        reSetLazyLoad()
    }

    /**
     * 重置所有的懒加载标示
     */
    fun reSetLazyLoad(){
        isInitializer = false
        isVisibility = false
        isFirstShow = true
    }

    /**
     * 当第一次可见时回调
     */
    open fun onFirstVisible(){}

    /**
     * 当不是第一次可见时回调
     */
    open fun onVisible(){}

    /**
     * 当不可见时回调
     */
    open fun onInVisible(){}


}
