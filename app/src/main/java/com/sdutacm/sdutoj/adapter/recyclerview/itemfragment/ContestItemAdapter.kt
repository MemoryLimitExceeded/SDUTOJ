package com.sdutacm.sdutoj.adapter.recyclerview.itemfragment

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.item.bean.ProblemItemBean
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity.Companion.PROBLEM_ITEM_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity.Companion.TITLE_TYPE

class ContestItemAdapter(data: List<SingleContestEntity>) : ListAdapter<SingleContestEntity>(data) {

    private enum class ContestStatus(
        val typeString: String, @ColorRes val colorRes: Int,
        @DrawableRes val drawableRes: Int
    ) {
        RUNNING("Running", R.color.red, R.drawable.ic_contest_status_running),
        PENDING("Pending", R.color.blue, R.drawable.ic_contest_status_waiting),
        FINISH("Finish", R.color.green, R.drawable.ic_contest_status_complete)
    }

    init {
        addItemType(TITLE_TYPE, R.layout.item_contest_title)
        addItemType(PROBLEM_ITEM_TYPE, R.layout.item_problem_list)
    }

    override fun convert(helper: QuickViewHolder, item: SingleContestEntity) {
        when (item.itemType) {
            (TITLE_TYPE) -> setTitle(helper, item)
            (PROBLEM_ITEM_TYPE) -> setProblemInfo(helper, item)
        }
    }

    private fun setProblemInfo(helper: QuickViewHolder, item: SingleContestEntity) {
        if (item.mContent is ProblemItemBean) {
            val title = when {
                (item.mProblemId != null) -> item.mProblemId + ". " + item.mContent.title
                else -> item.mContent.title
            }
            helper.setText(R.id.item_problem_list_title, title)
        } else {
            helper.setText(R.id.item_problem_list_title, "")
        }
        helper.setGone(R.id.item_problem_list_limit, false)
            .setGone(R.id.item_problem_list_create_date_ic, false)
            .setGone(R.id.item_problem_list_create_date, false)
            .setGone(R.id.item_problem_list_commit_count, false)
    }

    private fun setTitle(helper: QuickViewHolder, item: SingleContestEntity) {
        if (item.mContent is ContestBean) {
            val status = when {
                (System.currentTimeMillis() >= transToTimeStamp(item.mContent.start_time) &&
                        System.currentTimeMillis() <= transToTimeStamp(item.mContent.end_time))
                -> ContestStatus.RUNNING
                (System.currentTimeMillis() < transToTimeStamp(item.mContent.start_time))
                -> ContestStatus.PENDING
                else -> ContestStatus.FINISH
            }
            helper.setText(R.id.item_contest_title_content, item.mContent.name)
                .setText(R.id.item_contest_title_start_time_content, item.mContent.start_time)
                .setText(R.id.item_contest_title_end_time_content, item.mContent.end_time)
                .setText(R.id.item_contest_title_status_content, status.typeString)
                .setTextColor(R.id.item_contest_title_status_content, getResColor(status.colorRes))
                .setImageResource(R.id.item_contest_title_status_ic, status.drawableRes)
            var content = htmlToText(item.mContent.description)
            content = removeInvisibleChar(content)
            if (content != "") {
                helper.setText(R.id.item_contest_title_description, content)
            } else {
                helper.setGone(R.id.item_contest_title_description, false)
            }
        } else {
            helper.setText(R.id.item_contest_title_content, "")
                .setGone(R.id.item_contest_title_start_time, false)
                .setGone(R.id.item_contest_title_end_time, false)
                .setGone(R.id.item_contest_title_status, false)
                .setGone(R.id.item_contest_title_description, false)
        }
        helper.itemView.isClickable = false
    }

}