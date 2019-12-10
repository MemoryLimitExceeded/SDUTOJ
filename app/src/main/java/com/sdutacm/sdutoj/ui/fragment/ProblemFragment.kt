package com.sdutacm.sdutoj.ui.fragment

import android.view.View
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.recyclerview.fragment.ProblemAdapter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.ui.fragment.common.ListFragment
import com.sdutacm.sdutoj.item.entity.fragment.ProblemItemEntity
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.MIN_PROBLEM_PID
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.PROBLEM_INTERVAL
import com.sdutacm.sdutoj.mvp.main.presenter.ProblemPresenter
import com.sdutacm.sdutoj.ui.activity.MainActivity
import com.sdutacm.sdutoj.ui.fragment.item.ProblemItemFragment

class ProblemFragment : ListFragment<ProblemItemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int): ProblemFragment {
            val fragment = ProblemFragment()
            newInstance(contentLayoutId, fragment)
            return fragment
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
        if (mAdapter.data.size == 0) {
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
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = (adapter.data[position] as ProblemItemEntity).mProblemBean
            val itemFragment = ProblemItemFragment.newInstance(R.layout.item_fragment, data)
            val transaction =
                (activity as MainActivity?)?.supportFragmentManager?.beginTransaction()
            transaction?.add(
                R.id.fragment_container,
                ViewPagerFragment.newInstance(R.layout.fragment_view_pager, itemFragment)
            )
                ?.addToBackStack(null)
                ?.commit()
            (activity as MainActivity?)?.getFragmentContainer()?.visibility = View.VISIBLE
        }
    }

    private fun getData() {
        val args = mPresenter?.dataHelper as ProblemPresenter.ProblemDataHelper
        args.setPid(MIN_PROBLEM_PID)
            .setCmp(FragmentModel.CommonQueryParameters.CMP_GREATER_OR_EQUAL.parameters)
            .setOrder(FragmentModel.CommonQueryParameters.ORDER_ASC.parameters)
            .setLimit(PROBLEM_INTERVAL)
        mPresenter?.getData(args)
    }

    private fun getMoreData() {
        val data = mAdapter.getLastData()
        val args = mPresenter?.dataHelper as ProblemPresenter.ProblemDataHelper
        args.setLimit(PROBLEM_INTERVAL)
        if (data != null) {
            args.setPid(data.mProblemBean.pid)
                .setCmp(FragmentModel.CommonQueryParameters.CMP_GREATER.parameters)
                .setOrder(FragmentModel.CommonQueryParameters.ORDER_ASC.parameters)
            mPresenter?.getMoreData(args)
        } else {
            args.setPid(MIN_PROBLEM_PID)
                .setCmp(FragmentModel.CommonQueryParameters.CMP_GREATER_OR_EQUAL.parameters)
                .setOrder(FragmentModel.CommonQueryParameters.ORDER_ASC.parameters)
            mPresenter?.getMoreData(args)
        }
    }

}