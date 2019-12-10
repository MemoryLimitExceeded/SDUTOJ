package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.adapter.recyclerview.fragment.ContestAdapter
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.fragment.ContestItemEntity
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.mvp.main.model.ContestModel
import com.sdutacm.sdutoj.mvp.main.model.ContestModel.Companion.CONTEST_INTERVAL
import com.sdutacm.sdutoj.mvp.main.presenter.ContestPresenter

class ContestFragment : ListFragment<ContestItemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = ContestFragment()
            newInstance(contentLayoutId, fragment)
            return fragment
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
        val args = mPresenter?.dataHelper as ContestPresenter.ContentDataHelper
        args.setLimit(CONTEST_INTERVAL)
        mPresenter?.getData(null)
    }

    private fun getMoreData() {
        val data = mAdapter.getLastData()
        val args = mPresenter?.dataHelper as ContestPresenter.ContentDataHelper
        args.setLimit(CONTEST_INTERVAL)
        if (data != null) {
            args.setCid(data.mContestBean.cid)
                .setCmp(FragmentModel.CommonQueryParameters.CMP_LESS.parameters)
            mPresenter?.getMoreData(args)
        } else {
            mPresenter?.getMoreData(args)
        }
    }

}
