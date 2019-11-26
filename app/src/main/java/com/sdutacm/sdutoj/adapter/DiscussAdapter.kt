package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.DiscussItemEntity

class DiscussAdapter(data: List<DiscussItemEntity>) : ListAdapter<DiscussItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: DiscussItemEntity) {
    }
}