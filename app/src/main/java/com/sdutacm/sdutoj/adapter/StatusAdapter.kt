package com.sdutacm.sdutoj.adapter

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.bean.StatusBean
import com.sdutacm.sdutoj.item.entity.StatusItemEntity

class StatusAdapter(data: List<StatusItemEntity>) : ListAdapter<StatusItemEntity>(data) {

    init {
        addItemType(0, R.layout.item_status_list)
    }

    enum class StatusResult(val parameters: String, val result: Int) {
        WAITING("Waiting", 0),
        ACCEPTED("Accepted", 1),
        TIME_LIMIT_EXCEEDED("Time Limit Exceeded", 2),
        MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", 3),
        WRONG_ANSWER("Wrong Answer", 4),
        RUNTIME_ERROR("Runtime Error", 5),
        OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", 6),
        COMPILE_ERROR("Compile Error", 7),
        PRESENTATION_ERROR("Presentation Error", 8),
        SYSTEM_ERROR("System Error", 11),
        JUDGING("Judging", 12)
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
        val result: String =
            when (content.result) {
                (StatusResult.WAITING.result) -> StatusResult.WAITING.parameters
                (StatusResult.ACCEPTED.result) -> StatusResult.ACCEPTED.parameters
                (StatusResult.TIME_LIMIT_EXCEEDED.result) -> StatusResult.TIME_LIMIT_EXCEEDED.parameters
                (StatusResult.MEMORY_LIMIT_EXCEEDED.result) -> StatusResult.MEMORY_LIMIT_EXCEEDED.parameters
                (StatusResult.WRONG_ANSWER.result) -> StatusResult.WRONG_ANSWER.parameters
                (StatusResult.RUNTIME_ERROR.result) -> StatusResult.RUNTIME_ERROR.parameters
                (StatusResult.OUTPUT_LIMIT_EXCEEDED.result) -> StatusResult.OUTPUT_LIMIT_EXCEEDED.parameters
                (StatusResult.COMPILE_ERROR.result) -> StatusResult.COMPILE_ERROR.parameters
                (StatusResult.PRESENTATION_ERROR.result) -> StatusResult.PRESENTATION_ERROR.parameters
                (StatusResult.SYSTEM_ERROR.result) -> StatusResult.SYSTEM_ERROR.parameters
                (StatusResult.JUDGING.result) -> StatusResult.JUDGING.parameters
                else -> "--"
            }
        helper.setText(R.id.item_status_result_text, result)
        return this
    }

    private fun setDate(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(R.id.item_status_date, content.submission_time)
        return this
    }

    private fun setCodeLenInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_code_len,
            getXmlString(R.string.status_item_code_len).format(checkValue(content.code_length))
        )
        return this
    }

    private fun setMemoryInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_memory,
            getXmlString(R.string.status_item_memory).format(checkValue(content.memory))
        )
        return this
    }

    private fun setTimeInfo(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_time,
            getXmlString(R.string.status_item_time).format(checkValue(content.time))
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
            getXmlString(R.string.status_item_language).format(content.language)
        )
        return this
    }

    private fun setRunId(helper: QuickViewHolder, content: StatusBean): StatusAdapter {
        helper.setText(
            R.id.item_status_run_id,
            getXmlString(R.string.status_item_runid).format(content.runid)
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