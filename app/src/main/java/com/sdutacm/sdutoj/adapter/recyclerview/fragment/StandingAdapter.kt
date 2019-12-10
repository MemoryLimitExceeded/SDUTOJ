package com.sdutacm.sdutoj.adapter.recyclerview.fragment

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.fragment.StandingItemEntity

class StandingAdapter(data: List<StandingItemEntity>) : ListAdapter<StandingItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: StandingItemEntity) {
    }
}