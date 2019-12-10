package com.sdutacm.sdutoj.adapter.recyclerview.fragment

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.StatusBean
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity.Companion.DEFAULT_TYPE
import com.sdutacm.sdutoj.item.entity.fragment.StatusItemEntity

class StatusAdapter(data: List<StatusItemEntity>) : ListAdapter<StatusItemEntity>(data) {

    init {
        addItemType(DEFAULT_TYPE, R.layout.item_status_list)
    }

    enum class StatusResult(
        @ColorRes val colorRes: Int, @DrawableRes val drawableRes: Int, val parameters: String,
        val result: Int
    ) {
        WAITING(R.color.blue, R.drawable.ic_solution_result_waiting, "Waiting", 0),
        ACCEPTED(R.color.green, R.drawable.ic_solution_result_ac, "Accepted", 1),
        TIME_LIMIT_EXCEEDED(
            R.color.purple,
            R.drawable.ic_solution_result_tle,
            "Time Limit Exceeded",
            2
        ),
        MEMORY_LIMIT_EXCEEDED(
            R.color.gray,
            R.drawable.ic_solution_result_mle,
            "Memory Limit Exceeded",
            3
        ),
        WRONG_ANSWER(R.color.red, R.drawable.ic_solution_result_wa, "Wrong Answer", 4),
        RUNTIME_ERROR(R.color.brown, R.drawable.ic_solution_result_re, "Runtime Error", 5),
        OUTPUT_LIMIT_EXCEEDED(
            R.color.darkorange,
            R.drawable.ic_solution_result_ole,
            "Output Limit Exceeded",
            6
        ),
        COMPILE_ERROR(R.color.violet, R.drawable.ic_solution_result_ce, "Compile Error", 7),
        PRESENTATION_ERROR(
            R.color.yellow,
            R.drawable.ic_solution_result_pe,
            "Presentation Error",
            8
        ),
        SYSTEM_ERROR(R.color.darkslategray, R.drawable.ic_solution_result_se, "System Error", 11),
        JUDGING(R.color.hotpink, R.drawable.ic_solution_result_judging, "Judging", 12),
        DEFAULT(R.color.white, 0, "", 13)
    }

    override fun convert(helper: QuickViewHolder, item: StatusItemEntity) {
        val content = item.mStatusBean
        setRunId(helper, content)
            .setLanguageInfo(helper, content)
            .setUserInfo(helper, content)
            .setProblemInfo(helper, content)
            .setTimeInfo(helper, content)
            .setMemoryInfo(helper, content)
            .setCodeLenInfo(helper, content)
            .setDate(helper, content)
            .setResult(helper, content)
    }

    private fun setResult(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        val result: StatusResult =
            when (content.result) {
                (StatusResult.WAITING.result) -> StatusResult.WAITING
                (StatusResult.ACCEPTED.result) -> StatusResult.ACCEPTED
                (StatusResult.TIME_LIMIT_EXCEEDED.result) -> StatusResult.TIME_LIMIT_EXCEEDED
                (StatusResult.MEMORY_LIMIT_EXCEEDED.result) -> StatusResult.MEMORY_LIMIT_EXCEEDED
                (StatusResult.WRONG_ANSWER.result) -> StatusResult.WRONG_ANSWER
                (StatusResult.RUNTIME_ERROR.result) -> StatusResult.RUNTIME_ERROR
                (StatusResult.OUTPUT_LIMIT_EXCEEDED.result) -> StatusResult.OUTPUT_LIMIT_EXCEEDED
                (StatusResult.COMPILE_ERROR.result) -> StatusResult.COMPILE_ERROR
                (StatusResult.PRESENTATION_ERROR.result) -> StatusResult.PRESENTATION_ERROR
                (StatusResult.SYSTEM_ERROR.result) -> StatusResult.SYSTEM_ERROR
                (StatusResult.JUDGING.result) -> StatusResult.JUDGING
                else -> StatusResult.DEFAULT
            }
        helper.setText(R.id.item_status_result_text, result.parameters)
            .setTextColor(R.id.item_status_result_text, getResColor(result.colorRes))
            .setImageResource(R.id.item_status_result_ic, result.drawableRes)
        return this
    }

    private fun setDate(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(R.id.item_status_date, content.submission_time)
        return this
    }

    private fun setCodeLenInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_code_len,
            getResString(R.string.status_item_code_len).format(checkValue(content.code_length))
        )
        return this
    }

    private fun setMemoryInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_memory,
            getResString(R.string.status_item_memory).format(checkValue(content.memory))
        )
        return this
    }

    private fun setTimeInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_time,
            getResString(R.string.status_item_time).format(checkValue(content.time))
        )
        return this
    }

    private fun setProblemInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(R.id.item_status_problem_text, content.pid.toString())
        return this
    }

    private fun setUserInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(R.id.item_status_user_text, content.user_name)
        return this
    }

    private fun setLanguageInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_language,
            getResString(R.string.status_item_language).format(content.language)
        )
        return this
    }

    private fun setRunId(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_run_id,
            getResString(R.string.status_item_runid).format(content.runid)
        )
        return this
    }

    private fun checkValue(value: Int): String {
        if (value < 0) {
            return "--"
        } else {
            return value.toString()
        }
    }

}