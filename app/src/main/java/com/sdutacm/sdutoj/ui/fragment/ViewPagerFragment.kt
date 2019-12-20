package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.viewpager.ViewPagerItemAdapter
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity
import com.sdutacm.sdutoj.mvp.base.BaseDialogFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.fragment.common.ItemFragment

class ViewPagerFragment : BaseDialogFragment(), IMainContract.IMainView {

    private lateinit var mViewPager: ViewPager

    private lateinit var mAdapter: ViewPagerItemAdapter

    private lateinit var mChildFragment: ItemFragment<out ListItemEntity>

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, childFragment: ItemFragment<out ListItemEntity>): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            fragment.mChildFragment = childFragment
            newInstance(contentLayoutId, fragment)
            return fragment
        }

    }

    override fun initView(rootView: View) {
        mViewPager = rootView.findViewById(R.id.fragment_view_pager)
        mAdapter = ViewPagerItemAdapter(
            mChildFragment,
            childFragmentManager
        )
        mViewPager.adapter = mAdapter
    }

    override fun initPresenter() {
    }

    override fun initData() {
    }

    override fun updateData(data: Any?) {
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return null
    }

    override fun loadMoreData(data: Any) {
    }

    override fun initListener() {
    }

}