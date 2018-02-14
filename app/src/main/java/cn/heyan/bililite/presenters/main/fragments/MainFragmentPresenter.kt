package cn.heyan.bililite.presenters.main.fragments

import android.support.v4.view.PagerAdapter
import cn.heyan.bililite.presenters.main.adapter.HePagerAdapter
import cn.heyan.bililite.view.main.fragment.MainFragmentInt

/**
 * MainFragmentPresenter
 * Created by HeYan on 2018/2/14 0014.
 */

class MainFragmentPresenter(mainFragment:MainFragmentInt):MainFragmentPresenterInt{

    private val mainView = mainFragment

    override fun onCreateView() {
        val adapter = HePagerAdapter(mainView.getTheChildFragmentManager(), mainView.getTheTid())
        mainView.setPagerAdapter(adapter)
    }

}
