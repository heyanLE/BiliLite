package cn.heyan.bililite.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * LazyLoadFragment
 * 懒加载Fragment
 * 因为PageView有个预加载机制，会破坏其内的Fragment生命周期
 * 直接使用会造成不必要的浪费，所以需要懒加载机制
 * 当需要的时候才会加载数据
 * Created by HeYan on 2018/2/11 0011.
 */

abstract class LazyLoadFragment():Fragment(){

    //懒加载标识
    //是否已经实例化
    private var isInitializer = false
    //是否可见
    private var isVisibility = false
    //是否第一次可见
    private var isFirstVisibility = true
    
    //抽象变量 布局ID
    abstract val LAYOUT_ID : Int
    
    //可空变量，当前Fragment内的RootView
    private var rootView:View? = null



    /**
     * 重写onCreateView方法
     * @param inflater LayoutInflater? : 
     * @param container ViewGroup? : 
     * @param savedInstanceState Bundle? : 
     * @return rootView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(LAYOUT_ID,container,false)
        
        //是否已经实例化 -> 是
        isInitializer = true
        //是否可见 -> 返回当前可见状态
        isVisibility = userVisibleHint
        
        //如果当前状态为可见
        if (isVisibility){
            //如果是第一次可见
            if (isFirstVisibility){
                //调用OnFirstVisibility回调
                onFirstVisibility()
                //第一次可见标识为false
                isFirstVisibility = false
            }else{
                //调用OnVisibility回调
                onVisibility()
            }
        }
        
        
        return rootView
    }

    /**
     * 当Fragment被销毁时回调
     * 重置标识
     */
    override fun onDestroy() {
        super.onDestroy()
        resetLazyLoad()
    }

    /**
     * 一个回调函数，当Fragment可见状态改变时候回调
     * 值得注意的是，如果是绘制出来后不经过更改直接就是可见状态
     * 那么当他变为不可见之前不会回调这个方法
     * @param isVisibleToUser Boolean : 改变后可不可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        //如果这个Fragment还未实例化，直接Return，不进行操作
        if (! isInitializer){
            return
        }

        //如果由 不可见 -> 可见
        if (isVisibleToUser){
            //更改懒加载标识
            isVisibility = isVisibleToUser
            //如果是第一次可见
            if (isFirstVisibility){
                //第一次可见标识为false
                isFirstVisibility = false
                //回调OnFirstVisibility
                onFirstVisibility()
            }else{//如果不是
                //回调onVisibility
                onVisibility()
            }
        }else{//如果由可见 -> 不可见
            //更改标识
            isVisibility = isVisibleToUser
            //回调onInVisibility
            onInVisibility()
        }

    }
    
    /**
     * 重置懒加载标识
     */
    private fun resetLazyLoad(){
        isInitializer = false
        isVisibility = false
        isFirstVisibility = true
    }
    
    //抽象方法回调（懒加载机制实现主要方法）
    abstract fun onFirstVisibility()
    abstract fun onVisibility()
    abstract fun onInVisibility()

}
