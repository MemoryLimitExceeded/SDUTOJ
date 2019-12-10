package com.sdutacm.sdutoj.ui.fragment.common

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.viewpager.ViewPagerAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract

abstract class TabFragment : BaseFragment(), IMainContract.IMainView {

    protected lateinit var mTabLayout: TabLayout

    protected lateinit var mViewPager: ViewPager

    protected lateinit var mViewPagerAdapter: ViewPagerAdapter

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, fragment: BaseFragment) {
            BaseFragment.newInstance(contentLayoutId, fragment)
        }

    }

    protected abstract fun initViewPagerAdapter()

    protected abstract fun initTabLayout()

    protected abstract fun initViewPager()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter!!.attach(this)
    }

    override fun initListener() {
        mRetryButtonListener = View.OnClickListener {
            hideErrorLoading()
            getData()
        }
    }

    override fun loadMoreData(data: Any) {
    }

    override fun initView(rootView: View) {
        mTabLayout = rootView.findViewById(R.id.title_tablayout)
        mViewPager = rootView.findViewById(R.id.content_viewpager)
    }

    protected fun getData(){
        mPresenter?.getData(null)
    }

}