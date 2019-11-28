package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import com.sdutacm.sdutoj.adapter.ViewPagerAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.model.HomeModel
import com.sdutacm.sdutoj.mvp.main.model.HomeModel.HomeDataHelper
import com.sdutacm.sdutoj.mvp.main.presenter.FragmentPresenter
import com.sdutacm.sdutoj.ui.fragment.common.TabFragment
import com.sdutacm.sdutoj.utils.DataUtils

class HomeFragment : TabFragment() {

    private lateinit var mHomeData: HomeDataHelper

    companion object {
        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = HomeFragment()
            return newInstance(contentLayoutId, fragment)
        }
    }

    override fun updateData(data: Any?) {
        mHomeData = data as HomeDataHelper
    }

    override fun getModelFromView(): HomeModel {
        return HomeModel()
    }

    override fun initPresenter() {
        mPresenter = FragmentPresenter()
    }

    override fun initData() {
        mPresenter?.getData(null)
    }

    override fun initView(rootView: View) {
        super.initView(rootView)
        initViewPagerAdapter()
        initViewPager()
        initTabLayout()
    }

    override fun initViewPagerAdapter() {
        mViewPagerAdapter =
            ViewPagerAdapter(
                childFragmentManager,
                DataUtils.getMainTabTitle(),
                mHomeData.fragmentList
            )
    }

    override fun initViewPager() {
        mViewPager.adapter = mViewPagerAdapter
    }

    override fun initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager)
    }

}