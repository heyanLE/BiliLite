package cn.heyan.bililite.presenters.main.activities

import android.content.Context
import android.view.View
import android.widget.AdapterView

/**
 * MainActivityPresenterInt
 * Created by HeYan on 2018/2/14 0014.
 */

interface MainActivityPresenterInt{

    fun onViewCreate()
    fun onGridItemOnClick(adapterView:AdapterView<*>, view: View, i:Int, l:Long)
    fun onViewRecreate()
    fun viewRecreate()
    fun choOne()

}
