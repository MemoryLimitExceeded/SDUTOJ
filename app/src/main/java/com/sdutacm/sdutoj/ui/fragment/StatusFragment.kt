package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.adapter.recyclerview.fragment.StatusAdapter
import com.sdutacm.sdutoj.item.bean.StatusBean
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.fragment.StatusItemEntity
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.mvp.main.model.StatusModel
import com.sdutacm.sdutoj.mvp.main.model.StatusModel.Companion.STATUS_INTERVAL
import com.sdutacm.sdutoj.mvp.main.presenter.StatusPresenter

class StatusFragment : ListFragment<StatusItemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): StatusFragment {
            val fragment = StatusFragment()
            newInstance(contentLayoutId, fragment)
            return fragment
        }

    }

    override fun loadMoreData(data: Any) {
        if ((data as ArrayList<*>).size == STATUS_INTERVAL) {
            super.loadMoreData(data)
        } else {
            mAdapter.loadMoreEnd()
        }
        for (item in data) {
            mAdapter.addData(StatusItemEntity(item as StatusBean))
        }
    }

    override fun updateData(data: Any?) {
        val newData = ArrayList<StatusItemEntity>()
        for (item in (data as ArrayList<*>)) {
            newData.add(StatusItemEntity(item as StatusBean))
        }
        super.updateData(data)
        mAdapter.setNewData(newData)
    }

    override fun initPresenter() {
        mPresenter = StatusPresenter()
        mSemiDevelop = false
    }

    override fun initData() {
        if (mIsFirstCreate || mAdapter.data.size == 0) {
            showLoading()
            getMoreData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return StatusModel()
    }

    override fun initListener() {
        mRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener { getMoreData() }
        mRetryButtonListener = View.OnClickListener {
            hideErrorLoading()
            getMoreData()
        }
        mRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            setRefreshing(true)
            getData()
        }
    }

    override fun initAdapter() {
        mAdapter = StatusAdapter(ArrayList())
    }

    private fun getData() {
        val args = mPresenter?.dataHelper as StatusPresenter.StatusDataHelper
        args.setLimit(STATUS_INTERVAL)
        mPresenter?.getData(null)
    }

    private fun getMoreData() {
        val data = mAdapter.getLastData()
        val args = mPresenter?.dataHelper as StatusPresenter.StatusDataHelper
        args.setLimit(STATUS_INTERVAL)
        if (data != null) {
            args.setRunId(data.mStatusBean.runid)
                .setCmp(FragmentModel.CommonQueryParameters.CMP_LESS.parameters)
            mPresenter?.getMoreData(args)
        } else {
            mPresenter?.getMoreData(args)
        }
    }

}