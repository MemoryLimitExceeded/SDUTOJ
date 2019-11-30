package com.sdutacm.sdutoj.adapter.common

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity
import java.text.ParsePosition
import java.text.SimpleDateFormat

abstract class ListAdapter<T : ListItemEntity>(data: List<T>) :
    BaseMultiItemQuickAdapter<T, QuickViewHolder>(data) {

    fun getLastData(): T? {
        if (data.size > 0) {
            return data[data.size - 1]
        } else {
            return null
        }
    }

    protected fun getResString(@StringRes resInt: Int): String {
        return mContext.getString(resInt)
    }

    protected fun getResColor(@ColorRes resInt: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mContext.getColor(resInt)
        } else {
            mContext.resources.getColor(resInt)
        }
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SimpleDateFormat")
    protected fun transToTimeStamp(date: String): Long {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date, ParsePosition(0)).time
    }

}