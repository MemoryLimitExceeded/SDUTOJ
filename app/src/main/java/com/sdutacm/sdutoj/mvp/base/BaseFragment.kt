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

    protected var mIsViewInit = false

    companion object {

        private const val ARG_CONTENT_LAYOUT_ID = "ContentLayoutId"

        @JvmStatic
        fun newInstance(@LayoutRes contentLayoutId: Int, fragment: BaseFragment): BaseFragment {
            fragment.arguments = Bundle().apply {
                putInt(ARG_CONTENT_LAYOUT_ID, contentLayoutId)
            }
            return fragment
        }

    }

    protected abstract fun initPresenter()

    protected abstract fun initView(rootView: View)

    protected abstract fun initData()

    override fun hideLoading() {
        loadingView?.loadingViewSetVisibility(View.INVISIBLE)
    }

    override fun errorLoading() {
        loadingView?.loadingViewSetVisibility(View.INVISIBLE)
        loadingView?.dataErrorViewSetVisibility(View.VISIBLE)
    }

    override fun showLoading() {
        loadingView?.loadingViewSetVisibility(View.VISIBLE)
    }

    protected fun hideErrorLoading() {
        loadingView?.dataErrorViewSetVisibility(View.INVISIBLE)
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
        mIsViewInit = false
        return inflater.inflate(mContentLayoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = LoadingView(view, mRetryButtonListener)
        initData()
        initView(view)
        mIsViewInit = true
        mIsFirstCreate = false
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter?.detach()
        mPresenter = null
    }

}