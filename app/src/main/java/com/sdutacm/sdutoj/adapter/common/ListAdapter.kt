package com.sdutacm.sdutoj.adapter.common

import androidx.annotation.StringRes
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

abstract class ListAdapter<T : ListItemEntity>(data: List<T>) :
    BaseMultiItemQuickAdapter<T, QuickViewHolder>(data) {

    fun getLastData(): T? {
        if (data.size > 0) {
            return data[data.size - 1]
        } else {
            return null
        }
    }

    protected fun getXmlString(@StringRes resInt: Int): String {
        return mContext.getString(resInt)
    }

}