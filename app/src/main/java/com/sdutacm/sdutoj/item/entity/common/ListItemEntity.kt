package com.sdutacm.sdutoj.item.entity.common

import com.chad.library.adapter.base.entity.MultiItemEntity

abstract class ListItemEntity(private val itemType: Int) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }
}