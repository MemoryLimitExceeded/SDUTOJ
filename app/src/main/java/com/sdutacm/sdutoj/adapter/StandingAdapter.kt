package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.StandingItemEntity

class StandingAdapter(data: List<StandingItemEntity>) : ListAdapter<StandingItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: StandingItemEntity) {
    }
}