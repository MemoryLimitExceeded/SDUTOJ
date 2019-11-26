package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.adapter.ProblemAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.mvp.main.presenter.ProblemPresenter
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.entity.ProblemItemEntity
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.problemRequestStartPid

class ProblemFragment : ListFragment<ProblemItemEntity>() {

    private var mNeedProblemFirstIndex = problemRequestStartPid

    private var mInterval = 0

    private var mUpdateCount = 0

    companion object {
        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): BaseFragment {
            val fragment = ProblemFragment()
            return ListFragment.newInstance(contentLayoutId, fragment)
        }
    }

    override fun updateView(data: Any?) {
        if (data is Array<*>) {
            hideLoading()
            for (item: Any? in data) {
                if (item != null) {
                    mAdapter.addData(ProblemItemEntity(item as ProblemBean))
                }
            }
            mNeedProblemFirstIndex = mNeedProblemFirstIndex + mInterval + 1
        } else if (data is ProblemBean) {
            val newData = ProblemItemEntity(data)
            val index = mAdapter.data.indexOf(newData)
            mAdapter.setData(index, newData)
            mUpdateCount++
            if (mUpdateCount == mAdapter.data.size) {
                setRefreshing(false)
                mUpdateCount = 0
            }
        }
    }

    override fun initPresenter() {
        mPresenter = ProblemPresenter()
        mInterval = (mPresenter as ProblemPresenter).getInterval()
        mSemiDevelop = false
    }

    override fun initData() {
        if (mIsFirstCreate || mAdapter.data.size == 0) {
            addData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ProblemModel()
    }

    override fun initListener() {
        mRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener { addData() }
        mRetryButtonListener = View.OnClickListener {
            hideErrorLoading()
            addData()
        }
        mRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            updateData()
        }
    }

    override fun initAdapter() {
        mAdapter = ProblemAdapter(ArrayList(mInterval + 1))
    }

    private fun updateData() {
        mUpdateCount = 0
        setRefreshing(true)
        for (item in mAdapter.data) {
            mPresenter?.updateData(item.mProblemContent.pid)
        }
    }

    private fun addData() {
        showLoading()
        val data = mPresenter?.getData(mNeedProblemFirstIndex)
        if (data != null) {
            hideLoading()
            for (item: Any? in (data as Array<*>)) {
                if (item != null) {
                    mAdapter.addData(ProblemItemEntity(item as ProblemBean))
                }
            }
            mNeedProblemFirstIndex = mNeedProblemFirstIndex + mInterval + 1
        }
    }

}