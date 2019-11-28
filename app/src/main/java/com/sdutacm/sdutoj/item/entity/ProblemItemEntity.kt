package com.sdutacm.sdutoj.item.entity

import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

class ProblemItemEntity(val mProblemContent: ProblemBean) : ListItemEntity(0) {

    override fun equals(other: Any?): Boolean {
        return mProblemContent.pid == (other as ProblemItemEntity).mProblemContent.pid
    }

    override fun hashCode(): Int {
        return mProblemContent.hashCode()
    }

}