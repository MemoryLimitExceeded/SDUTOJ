package com.sdutacm.sdutoj.ui.fragment.common

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.IMainContract

abstract class ItemFragment<T : ListItemEntity> : BaseFragment(), IMainContract.IMainView {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mRecyclerView: RecyclerView

    private var mRefreshListener: SwipeRefreshLayout.OnRefreshListener? = null

    protected var data: Parcelable? = null

    protected lateinit var mAdapter: ListAdapter<T>

    companion object {

        private const val ARG_BEAN = "Bean"

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, fragment: BaseFragment, bean: Parcelable) {
            newInstance(contentLayoutId, fragment)
            fragment.arguments?.putParcelable(ARG_BEAN, bean)
        }

    }

    protected abstract fun getData()

    protected abstract fun setData()

    protected abstract fun initAdapter()

    protected fun setRefreshing(refreshing: Boolean) {
        mSwipeRefreshLayout.isRefreshing = refreshing
    }

    protected fun getRefreshing(): Boolean {
        return mSwipeRefreshLayout.isRefreshing
    }

    override fun hideLoading() {
        if (!mSwipeRefreshLayout.isRefreshing) {
            super.hideLoading()
        } else {
            setRefreshing(false)
            Toast.makeText(context, R.string.get_data_success_hint, Toast.LENGTH_SHORT).show()
        }
    }

    override fun errorLoading() {
        if (!mSwipeRefreshLayout.isRefreshing) {
            super.errorLoading()
        } else {
            setRefreshing(false)
            Toast.makeText(context, R.string.get_data_error_hint, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPresenter!!.attach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { data = it.getParcelable(ARG_BEAN) }
    }

    override fun initView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.item_fragment_recyclerview)
        mSwipeRefreshLayout = rootView.findViewById(R.id.item_fragment_swiperefreshlayout)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mSwipeRefreshLayout.setOnRefreshListener(mRefreshListener)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue)
    }

    override fun initListener() {
        mRetryButtonListener = View.OnClickListener {
            hideErrorLoading()
            getData()
        }
        mRefreshListener = SwipeRefreshLayout.OnRefreshListener {
            setRefreshing(true)
            getData()
        }
    }

    override fun onlyFirstCreate() {
        initAdapter()
        super.onlyFirstCreate()
    }

}