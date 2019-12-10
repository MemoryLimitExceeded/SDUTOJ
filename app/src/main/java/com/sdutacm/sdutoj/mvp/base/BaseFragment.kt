package com.sdutacm.sdutoj.mvp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.sdutacm.sdutoj.mvp.main.presenter.FragmentPresenter
import com.sdutacm.sdutoj.ui.widget.LoadingView

abstract class BaseFragment : Fragment(),
    IBaseContract.IBaseView {

    var loadingView: LoadingView? = null

    private var mContentLayoutId = 0

    protected var mIsFirstCreate = true

    protected var mPresenter: FragmentPresenter? = null

    protected var mRetryButtonListener: View.OnClickListener? = null

    companion object {

        private const val ARG_CONTENT_LAYOUT_ID = "ContentLayoutId"

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, fragment: BaseFragment) {
            fragment.arguments = Bundle().apply {
                putInt(ARG_CONTENT_LAYOUT_ID, contentLayoutId)
            }
        }

    }

    protected abstract fun initPresenter()

    protected abstract fun initView(rootView: View)

    protected abstract fun initData()

    protected abstract fun initListener()

    override fun hideLoading() {
        loadingView?.loadingViewSetVisibility(View.GONE)
    }

    override fun errorLoading() {
        loadingView?.loadingViewSetVisibility(View.GONE)
        loadingView?.dataErrorViewSetVisibility(View.VISIBLE)
    }

    override fun showLoading() {
        loadingView?.loadingViewSetVisibility(View.VISIBLE)
    }

    protected fun hideErrorLoading() {
        loadingView?.dataErrorViewSetVisibility(View.GONE)
        loadingView?.loadingViewSetVisibility(View.VISIBLE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mContentLayoutId = it.getInt(ARG_CONTENT_LAYOUT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(mContentLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIsFirstCreate) {
            onlyFirstCreate()
        }
        loadingView = LoadingView(view, mRetryButtonListener)
        initData()
        initView(view)
        mIsFirstCreate = false
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter?.detach()
        mPresenter = null
    }

    protected open fun onlyFirstCreate() {
        initListener()
    }

}