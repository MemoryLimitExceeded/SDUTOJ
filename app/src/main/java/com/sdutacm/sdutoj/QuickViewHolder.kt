package com.sdutacm.sdutoj

import android.view.View
import androidx.annotation.IdRes
import com.chad.library.adapter.base.BaseViewHolder

class QuickViewHolder(view: View) : BaseViewHolder(view) {

    fun setFocusable(@IdRes viewId: Int, enable:Boolean): QuickViewHolder {
        val view = getView<View>(viewId)
        view.isFocusable = enable
        return this
    }

    fun setClickable(@IdRes viewId: Int, enable:Boolean): QuickViewHolder {
        val view = getView<View>(viewId)
        view.isClickable = enable
        return this
    }

}