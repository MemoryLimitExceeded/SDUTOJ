package com.sdutacm.sdutoj.item.entity.itemfragment

import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

class SingleContestEntity(val mContent: Any, val mProblemId: String?, itemType: Int) :
    ListItemEntity(itemType) {

    companion object {

        const val TITLE_TYPE = 1

        const val PROBLEM_ITEM_TYPE = 2

    }

}