package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.UserItemEntity

class UserAdapter(data: List<UserItemEntity>) : ListAdapter<UserItemEntity>(data) {
    override fun convert(helper: QuickViewHolder, item: UserItemEntity) {
    }
}