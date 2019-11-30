package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.adapter.ProblemAdapter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.ProblemItemEntity
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.MIN_PROBLEM_PID
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.PROBLEM_INTERVAL
import com.sdutacm.sdutoj.mvp.main.presenter.ProblemPresenter

class ProblemFragment : ListFragment<ProblemItemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = ProblemFragment()
            return newInstance(contentLayoutId, fragment)
        }

    }

    override fun loadMoreData(data: Any) {
        if ((data as ArrayList<*>).size == PROBLEM_INTERVAL) {
            super.loadMoreData(data)
        } else {
            mAdapter.loadMoreEnd()
        }
        for (item in data) {
            mAdapter.addData(ProblemItemEntity(item as ProblemBean))
        }
    }

    override fun updateData(data: Any?) {
        val newData = ArrayList<ProblemItemEntity>()
        for (item in (data as ArrayList<*>)) {
            newData.add(ProblemItemEntity(item as ProblemBean))
        }
        super.updateData(data)
        mAdapter.setNewData(newData)
    }

    override fun initPresenter() {
        mPresenter = ProblemPresenter()
        mSemiDevelop = false
    }

    override fun initData() {
        if (mIsFirstCreate || mAdapter.data.size == 0) {
            showLoading()
            getMoreData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ProblemModel()
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
        mAdapter = ProblemAdapter(ArrayList())
    }

    private fun getData() {
        mPresenter?.getData(null)
    }

    private fun getMoreData() {
        val data = mAdapter.getLastData()
        if (data != null) {
            mPresenter?.getMoreData(data.mProblemBean.pid)
        } else {
            mPresenter?.getMoreData(MIN_PROBLEM_PID)
        }
    }

}