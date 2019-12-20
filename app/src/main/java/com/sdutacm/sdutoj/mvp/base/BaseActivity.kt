package com.sdutacm.sdutoj.mvp.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sdutacm.sdutoj.data.database.SQLiteHelper
import com.sdutacm.sdutoj.ui.widget.LoadingView

abstract class BaseActivity<P : BasePresenter<out IBaseContract.IBaseView, out IBaseContract.IBaseModel>> :
    AppCompatActivity(), IBaseContract.IBaseView {

    private var loadingView: LoadingView? = null

    protected var mPresenter: P? = null

    protected var mRetryButtonListener: View.OnClickListener? = null

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initPresenter()

    protected abstract fun initData()

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

    protected fun showNoPermission(){
        loadingView?.loadingViewSetVisibility(View.GONE)
        loadingView?.noPermissionView(View.VISIBLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        SQLiteHelper.newInstance(application)
        initPresenter()
        initData()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
        mPresenter = null
    }

}