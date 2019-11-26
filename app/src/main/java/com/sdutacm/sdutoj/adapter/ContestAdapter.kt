package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.ContestItemEntity

class ContestAdapter(data: List<ContestItemEntity>) : ListAdapter<ContestItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: ContestItemEntity) {
    }
}