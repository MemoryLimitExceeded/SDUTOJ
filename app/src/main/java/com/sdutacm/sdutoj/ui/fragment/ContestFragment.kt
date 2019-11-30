package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.adapter.ContestAdapter
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.ContestItemEntity
import com.sdutacm.sdutoj.mvp.main.model.ContestModel
import com.sdutacm.sdutoj.mvp.main.model.ContestModel.Companion.CONTEST_INTERVAL
import com.sdutacm.sdutoj.mvp.main.presenter.ContestPresenter

class ContestFragment : ListFragment<ContestItemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = ContestFragment()
            return newInstance(contentLayoutId, fragment)
        }

    }

    override fun loadMoreData(data: Any) {
        if ((data as ArrayList<*>).size == CONTEST_INTERVAL) {
            super.loadMoreData(data)
        } else {
            mAdapter.loadMoreEnd()
        }
        for (item in data) {
            mAdapter.addData(ContestItemEntity(item as ContestBean))
        }
    }

    override fun updateData(data: Any?) {
        val newData = ArrayList<ContestItemEntity>()
        for (item in (data as ArrayList<*>)) {
            newData.add(ContestItemEntity(item as ContestBean))
        }
        super.updateData(data)
        mAdapter.setNewData(newData)
    }

    override fun initPresenter() {
        mPresenter = ContestPresenter()
        mSemiDevelop = false
    }

    override fun initData() {
        if (mIsFirstCreate || mAdapter.data.size == 0) {
            showLoading()
            getMoreData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ContestModel()
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
        mAdapter = ContestAdapter(ArrayList())
    }

    private fun getData() {
        mPresenter?.getData(null)
    }

    private fun getMoreData() {
        val data = mAdapter.getLastData()
        if (data != null) {
            mPresenter?.getMoreData(data.mContestBean.cid)
        } else {
            mPresenter?.getMoreData(-1)
        }
    }

}
