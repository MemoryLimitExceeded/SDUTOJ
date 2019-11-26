package com.sdutacm.sdutoj.adapter

import androidx.annotation.StringRes
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.ProblemItemEntity

class ProblemAdapter(data: List<ProblemItemEntity>) : ListAdapter<ProblemItemEntity>(data) {

    init {
        addItemType(0, R.layout.item_problem_list)
    }

    override fun convert(helper: QuickViewHolder, item: ProblemItemEntity) {
        val content = item.mProblemContent
        helper.setText(
            R.id.item_title,
            getXmlString(R.string.problem_item_title).format(content.pid, content.title)
        )
            .setText(
                R.id.item_limit,
                getXmlString(R.string.problem_item_limit).format(
                    content.time_limit,
                    content.memory_limit
                )
            )
            .setText(R.id.item_create_date, content.added_time)
            .setText(
                R.id.item_commit_count,
                getXmlString(R.string.problem_item_commit_count).format(
                    content.accepted * 100.0 / content.submission,
                    content.accepted,
                    content.submission
                )
            )
    }

    private fun getXmlString(@StringRes resInt: Int): String {
        return mContext.getString(resInt)
    }
}