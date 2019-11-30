package com.sdutacm.sdutoj.adapter

import androidx.annotation.ColorRes
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.item.entity.ContestItemEntity

class ContestAdapter(data: List<ContestItemEntity>) : ListAdapter<ContestItemEntity>(data) {

    companion object {

        private const val DEFAULT_DATE = "0000-00-00 00:00:00"

    }

    enum class ContentType(val typeId: Int, val typeString: String, @ColorRes val colorRes: Int) {
        PRIVATE(1, "Private", R.color.red),
        REGISTER(2, "Register", R.color.blue),
        PUBLIC(3, "Public", R.color.green)
    }

    init {
        addItemType(0, R.layout.item_contest_list)
    }

    override fun convert(helper: QuickViewHolder, item: ContestItemEntity) {
        val content = item.mContestBean
        setTitle(helper, content)
            .setCID(helper, content)
            .setContestStatus(helper, content)
            .setContentType(helper, content)
            .setRegisterDate(helper, content)
            .setRunDate(helper, content)
    }

    private fun setRunDate(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        helper.setText(
            R.id.item_contest_run_date_text,
            content.start_time + "\n" + content.end_time
        ).setImageResource(R.id.item_contest_run_date_ic,R.drawable.ic_content_run_date)
        return this
    }

    private fun setRegisterDate(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        if (content.register_start_time != DEFAULT_DATE && content.register_end_time != DEFAULT_DATE
        ) {
            helper.setText(
                R.id.item_contest_register_date_text,
                content.register_start_time + "\n" + content.register_end_time
            ).setVisible(R.id.item_contest_register_date_text, true)
                .setImageResource(R.id.item_contest_register_date_ic,R.drawable.ic_contest_register_date)
                .setGone(R.id.item_contest_register_date_ic,true)
        } else {
            helper.setGone(R.id.item_contest_register_date_text, false)
                .setGone(R.id.item_contest_register_date_ic,false)
        }
        return this
    }

    private fun setContentType(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        val type = when (content.type) {
            (ContentType.PRIVATE.typeId) -> ContentType.PRIVATE
            (ContentType.REGISTER.typeId) -> ContentType.REGISTER
            (ContentType.PUBLIC.typeId) -> ContentType.PUBLIC
            else -> ContentType.PUBLIC
        }
        helper.setText(R.id.item_contest_type, type.typeString)
            .setTextColor(R.id.item_contest_type, getResColor(type.colorRes))
        return this
    }

    private fun setContestStatus(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        if (System.currentTimeMillis() >= transToTimeStamp(content.start_time) && System.currentTimeMillis() <= transToTimeStamp(
                content.end_time
            )
        ) {
            helper.setImageResource(R.id.item_contest_status, R.drawable.ic_contest_status_running)
        } else if (System.currentTimeMillis() < transToTimeStamp(content.start_time)) {
            helper.setImageResource(R.id.item_contest_status, R.drawable.ic_contest_status_waiting)
        } else {
            helper.setImageResource(R.id.item_contest_status, R.drawable.ic_contest_status_complete)
        }
        return this
    }

    private fun setCID(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        helper.setText(R.id.item_contest_cid_text, content.cid.toString())
            .setImageResource(R.id.item_contest_cid_ic, R.drawable.ic_contest_cid)
        return this
    }

    private fun setTitle(helper: QuickViewHolder, content: ContestBean): ContestAdapter {
        helper.setText(R.id.item_contest_title, content.name)
        return this
    }

}