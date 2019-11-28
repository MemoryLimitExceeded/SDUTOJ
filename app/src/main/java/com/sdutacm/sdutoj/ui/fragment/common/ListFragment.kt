package com.sdutacm.sdutoj.ui.fragment.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.item.entity.ProblemItemEntity
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

abstract class ListFragment<T : ListItemEntity> : BaseFragment(), IMainContract.IMainView {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mManager: RecyclerView.LayoutManager

    protected lateinit var mAdapter: ListAdapter<T>

    protected var mRequestLoadMoreListener: BaseQuickAdapter.RequestLoadMoreListener? = null

    protected var mRefreshListener: SwipeRefreshLayout.OnRefreshListener? = null

    protected var mSemiDevelop = true

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, fragment: BaseFragment): BaseFragment {
            return BaseFragment.newInstance(contentLayoutId, fragment)
        }

    }

    protected abstract fun initAdapter()

    protected abstract fun initListener()

    protected fun setRefreshing(refreshing: Boolean) {
        mSwipeRefreshLayout.isRefreshing = refreshing
        mAdapter.setEnableLoadMore(!refreshing)
    }

    override fun updateData(data: Any?) {
        hideLoading()
    }

    override fun loadMoreData(data: Any) {
        hideLoading()
    }

    override fun hideLoading() {
        if (mAdapter.data.size == 0) {
            super.hideLoading()
        } else {
            if (mAdapter.isLoading) {
                mAdapter.loadMoreComplete()
            } else if (mSwipeRefreshLayout.isRefreshing) {
                setRefreshing(false)
                Toast.makeText(context, R.string.get_data_success_hint, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun showLoading() {
        if (mAdapter.data.size == 0) {
            super.showLoading()
        }
    }

    override fun errorLoading() {
        if (mAdapter.data.size == 0) {
            super.errorLoading()
        } else {
            if (mAdapter.isLoading) {
                mAdapter.loadMoreFail()
            } else if (mSwipeRefreshLayout.isRefreshing) {
                setRefreshing(false)
                Toast.makeText(context, R.string.get_data_error_hint, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter?.attach(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mIsFirstCreate) {
            initAdapter()
            initListener()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.fragment_recyclerview)
        mSwipeRefreshLayout = rootView.findViewById(R.id.fragment_swiperefreshlayout)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mAdapter.setOnLoadMoreListener(mRequestLoadMoreListener, mRecyclerView)
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue)
        mManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = mManager
        if (mSemiDevelop) {
            loadingView?.stayTunedViewSetVisibility(View.VISIBLE)
        }
    }

}