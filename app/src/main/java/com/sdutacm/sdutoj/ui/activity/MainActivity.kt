package com.sdutacm.sdutoj.ui.activity

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.viewpager.ViewPagerAdapter
import com.sdutacm.sdutoj.mvp.base.BaseActivity
import com.sdutacm.sdutoj.mvp.main.model.ActivityModel.ActivityDataHelper
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ActivityModel
import com.sdutacm.sdutoj.mvp.main.presenter.ActivityPresenter

class MainActivity : BaseActivity<ActivityPresenter>(), IMainContract.IMainView {

    private lateinit var mTabLayout: TabLayout

    private lateinit var mViewPager: ViewPager

    private lateinit var mViewPagerAdapter: ViewPagerAdapter

    private lateinit var mViewData: ActivityDataHelper

    override fun loadMoreData(data: Any) {
    }

    override fun updateData(data: Any?) {
        mViewData = data as ActivityDataHelper
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ActivityModel()
    }

    override fun initView() {
        mTabLayout = findViewById(R.id.title_tablayout)
        mViewPager = findViewById(R.id.content_viewpager)
        initViewPagerAdapter()
        initViewPager()
        initTabLayout()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        mPresenter = ActivityPresenter()
        mPresenter!!.attach(this)
    }

    override fun initData() {
        mPresenter?.getData(null)
    }

    private fun initViewPagerAdapter() {
        mViewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            mViewData.titleList,
            mViewData.fragmentList
        )
    }

    private fun initViewPager() {
        mViewPager.adapter = mViewPagerAdapter
    }

    private fun initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager)
    }

}
