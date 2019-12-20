package com.sdutacm.sdutoj.ui.widget

import android.view.View
import android.view.ViewStub
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.sdutacm.sdutoj.R

class LoadingView(rootView: View, private val mRetryButtonListener: View.OnClickListener?) {

    private var mLoadingView: View

    private var mDataErrorView: View

    private var mNoDataView: View

    private var mStayTunedView: View

    private var mNoPermissionView: View

    private var mIsFirstSetLoadingView = true

    private var mIsFirstSetDataErrorView = true

    private var mIsFirstSetNoDataView = true

    private var mIsFirstSetStayTunedView = true

    private var mIsFirstSetNoPermissionView = true

    init {
        mLoadingView = rootView.findViewById(R.id.view_stub_loading)
        mDataErrorView = rootView.findViewById(R.id.view_stub_get_data_error)
        mNoDataView = rootView.findViewById(R.id.view_stub_no_data)
        mStayTunedView = rootView.findViewById(R.id.view_stub_stay_tuned)
        mNoPermissionView = rootView.findViewById(R.id.view_stub_no_permission)
    }

    fun loadingViewSetVisibility(visibility: Int) {
        if (mIsFirstSetLoadingView) {
            mLoadingView = (mLoadingView as ViewStub).inflate()
            val imageView: AppCompatImageView = mLoadingView.findViewById(R.id.loading_imageview)
            imageView.setImageResource(R.drawable.ic_data_loading)
            val textView: AppCompatTextView = mLoadingView.findViewById(R.id.loading_textview)
            textView.setText(R.string.data_loading)
            mIsFirstSetLoadingView = false
        }
        mLoadingView.visibility = visibility
    }

    fun dataErrorViewSetVisibility(visibility: Int) {
        if (mIsFirstSetDataErrorView) {
            mDataErrorView = (mDataErrorView as ViewStub).inflate()
            val imageView: AppCompatImageView = mDataErrorView.findViewById(R.id.loading_imageview)
            imageView.setImageResource(R.drawable.ic_get_data_error)
            val textView: AppCompatTextView = mDataErrorView.findViewById(R.id.loading_textview)
            textView.setText(R.string.get_data_error_hint)
            val button: AppCompatButton = mDataErrorView.findViewById(R.id.loading_button)
            button.visibility = View.VISIBLE
            if (mRetryButtonListener != null) {
                button.setOnClickListener(mRetryButtonListener)
            }
            mIsFirstSetDataErrorView = false
        }
        mDataErrorView.visibility = visibility
    }

    fun noDataViewSetVisibility(visibility: Int) {
        if (mIsFirstSetNoDataView) {
            mNoDataView = (mNoDataView as ViewStub).inflate()
            val imageView: AppCompatImageView = mNoDataView.findViewById(R.id.loading_imageview)
            imageView.setImageResource(R.drawable.ic_no_data)
            val textView: AppCompatTextView = mNoDataView.findViewById(R.id.loading_textview)
            textView.setText(R.string.no_data_hint)
            mIsFirstSetNoDataView = false
        }
        mNoDataView.visibility = visibility
    }

    fun stayTunedViewSetVisibility(visibility: Int) {
        if (mIsFirstSetStayTunedView) {
            mStayTunedView = (mStayTunedView as ViewStub).inflate()
            val imageView: AppCompatImageView = mStayTunedView.findViewById(R.id.loading_imageview)
            imageView.setImageResource(R.drawable.ic_stay_tuned)
            val textView: AppCompatTextView = mStayTunedView.findViewById(R.id.loading_textview)
            textView.setText(R.string.stay_tuned_hint)
            mIsFirstSetStayTunedView = false
        }
        mStayTunedView.visibility = visibility
    }

    fun noPermissionView(visibility: Int) {
        if (mIsFirstSetNoPermissionView) {
            mNoPermissionView = (mNoPermissionView as ViewStub).inflate()
            val imageView: AppCompatImageView =
                mNoPermissionView.findViewById(R.id.loading_imageview)
            imageView.setImageResource(R.drawable.ic_no_permission)
            val textView: AppCompatTextView = mNoPermissionView.findViewById(R.id.loading_textview)
            textView.setText(R.string.no_permission_hint)
            mIsFirstSetNoPermissionView = false
        }
        mNoPermissionView.visibility = visibility
    }

}