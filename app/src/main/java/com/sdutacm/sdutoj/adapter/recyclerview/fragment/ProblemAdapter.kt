package com.sdutacm.sdutoj.adapter.recyclerview.fragment

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity.Companion.DEFAULT_TYPE
import com.sdutacm.sdutoj.item.entity.fragment.ProblemItemEntity

class ProblemAdapter(data: List<ProblemItemEntity>) : ListAdapter<ProblemItemEntity>(data) {

    init {
        addItemType(DEFAULT_TYPE, R.layout.item_problem_list)
    }

    override fun convert(helper: QuickViewHolder, item: ProblemItemEntity) {
        val content = item.mProblemBean
        setTitle(helper, content)
        setLimit(helper, content)
        setDate(helper, content)
        setSubmission(helper, content)
    }

    private fun setSubmission(helper: QuickViewHolder, content: ProblemBean) {
        helper.setText(
            R.id.item_problem_list_commit_count,
            getResString(R.string.problem_item_commit_count).format(
                content.accepted * 100 / checkDivisor(content.submission),
                content.accepted,
                content.submission
            )
        )
    }

    private fun setDate(helper: QuickViewHolder, content: ProblemBean) {
        helper.setText(R.id.item_problem_list_create_date, content.added_time)
    }

    private fun setLimit(helper: QuickViewHolder, content: ProblemBean) {
        helper.setText(
            R.id.item_problem_list_limit,
            getResString(R.string.problem_item_limit).format(
                content.time_limit,
                content.memory_limit
            )
        )
    }

    private fun setTitle(helper: QuickViewHolder, content: ProblemBean) {
        helper.setText(
            R.id.item_problem_list_title,
            getResString(R.string.problem_item_title).format(content.pid, content.title)
        )
    }

    private fun checkDivisor(submission: Int): Double {
        if (submission == 0) {
            return 1.0
        } else {
            return submission * 1.0
        }
    }

}