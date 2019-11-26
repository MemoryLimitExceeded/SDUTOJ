package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.StatusItemEntity

class StatusAdapter(data: List<StatusItemEntity>) : ListAdapter<StatusItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: StatusItemEntity) {
    }
}