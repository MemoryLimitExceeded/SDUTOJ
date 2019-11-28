package com.sdutacm.sdutoj.ui.fragment

import androidx.annotation.LayoutRes
import com.sdutacm.sdutoj.adapter.StatusAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.StatusItemEntity
import com.sdutacm.sdutoj.mvp.main.model.StatusModel
import com.sdutacm.sdutoj.mvp.main.presenter.FragmentPresenter

class StatusFragment : ListFragment<StatusItemEntity>() {

    companion object {
        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = StatusFragment()
            return ListFragment.newInstance(contentLayoutId, fragment)
        }
    }

    override fun initListener() {
//        mRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener { loadModeData() }
//        mRetryButtonListener = View.OnClickListener {
//            hideErrorLoading()
//            getData()
//        }
//        mRefreshListener = SwipeRefreshLayout.OnRefreshListener { getData() }
    }

    override fun initPresenter() {
        mPresenter = FragmentPresenter()
        //mSemiDevelop = false
    }

    override fun initData() {
        if (mIsFirstCreate || mAdapter.data.size == 0) {
           // getData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return StatusModel()
    }

    override fun initAdapter() {
        mAdapter = StatusAdapter(ArrayList())
    }

    private fun getData() {
        mPresenter?.getData(null)
    }

    private fun loadModeData() {
        val data = mAdapter.getLastData()
        if (data != null) {
            mPresenter?.getData(data.mStatusBean.runid)
        }
    }

}