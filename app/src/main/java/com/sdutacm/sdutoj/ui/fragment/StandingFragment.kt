package com.sdutacm.sdutoj.ui.fragment

import androidx.annotation.LayoutRes
import com.sdutacm.sdutoj.adapter.StandingAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.presenter.FragmentPresenter
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.StandingItemEntity

class StandingFragment : ListFragment<StandingItemEntity>() {

    companion object {
        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = StandingFragment()
            return ListFragment.newInstance(contentLayoutId, fragment)
        }
    }

    override fun initListener() {
    }

    override fun initPresenter() {
        mPresenter = FragmentPresenter()
    }

    override fun initData() {
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return null
    }

    override fun initAdapter() {
        mAdapter = StandingAdapter(ArrayList())
    }

}