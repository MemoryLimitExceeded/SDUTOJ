package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.entity.ProblemItemEntity

class ProblemAdapter(data: List<ProblemItemEntity>) : ListAdapter<ProblemItemEntity>(data) {

    init {
        addItemType(0, R.layout.item_problem_list)
    }

    override fun convert(helper: QuickViewHolder, item: ProblemItemEntity) {
        val content = item.mProblemContent
        setTitle(helper, content)
            .setLimit(helper, content)
            .setDate(helper, content)
            .setSubmission(helper, content)
    }

    private fun setSubmission(helper: QuickViewHolder, content: ProblemBean): ProblemAdapter {
        helper.setText(
            R.id.item_problem_commit_count,
            getXmlString(R.string.problem_item_commit_count).format(
                content.accepted * 100 / checkDivisor(content.submission),
                content.accepted,
                content.submission
            )
        )
        return this
    }

    private fun setDate(helper: QuickViewHolder, content: ProblemBean): ProblemAdapter {
        helper.setText(R.id.item_problem_create_date, content.added_time)
        return this
    }

    private fun setLimit(helper: QuickViewHolder, content: ProblemBean): ProblemAdapter {
        helper.setText(
            R.id.item_problem_limit,
            getXmlString(R.string.problem_item_limit).format(
                content.time_limit,
                content.memory_limit
            )
        )
        return this
    }

    private fun setTitle(helper: QuickViewHolder, content: ProblemBean): ProblemAdapter {
        helper.setText(
            R.id.item_problem_title,
            getXmlString(R.string.problem_item_title).format(content.pid, content.title)
        )
        return this
    }

    private fun checkDivisor(submission: Int): Double {
        if (submission == 0) {
            return 1.0
        } else {
            return submission * 1.0
        }
    }

}