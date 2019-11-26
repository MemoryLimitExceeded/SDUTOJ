package com.sdutacm.sdutoj.adapter.common

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

abstract class ListAdapter<T : ListItemEntity>(data: List<T>) :
    BaseMultiItemQuickAdapter<T, QuickViewHolder>(data) {
}