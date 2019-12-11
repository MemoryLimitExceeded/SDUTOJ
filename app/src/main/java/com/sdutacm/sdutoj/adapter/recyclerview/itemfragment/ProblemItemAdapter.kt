package com.sdutacm.sdutoj.adapter.recyclerview.itemfragment

import android.os.Build
import android.text.Html
import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.DESCRIPTION_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.HINT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SOURCE_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.TITLE_TYPE

class ProblemItemAdapter(data: List<SingleProblemEntity>) : ListAdapter<SingleProblemEntity>(data) {

    init {
        addItemType(TITLE_TYPE, R.layout.item_problem_title)
        addItemType(DESCRIPTION_TYPE, R.layout.title_with_text)
        addItemType(INPUT_TYPE, R.layout.title_with_text)
        addItemType(OUTPUT_TYPE, R.layout.title_with_text)
        addItemType(SAMPLE_INPUT_TYPE, R.layout.title_with_text)
        addItemType(SAMPLE_OUTPUT_TYPE, R.layout.title_with_text)
        addItemType(HINT_TYPE, R.layout.title_with_text)
        addItemType(SOURCE_TYPE, R.layout.title_with_text)
    }

    override fun convert(helper: QuickViewHolder, item: SingleProblemEntity) {
        when (item.itemType) {
            (TITLE_TYPE) -> setTitle(helper, item)
            (DESCRIPTION_TYPE) -> setDescription(helper, item)
            (INPUT_TYPE) -> setInput(helper, item)
            (OUTPUT_TYPE) -> setOutput(helper, item)
            (SAMPLE_INPUT_TYPE) -> setSampleInput(helper, item)
            (SAMPLE_OUTPUT_TYPE) -> setSampleOutput(helper, item)
            (HINT_TYPE) -> setHint(helper, item)
            (SOURCE_TYPE) -> setSource(helper, item)
        }
    }

    private fun setSource(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_source_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setHint(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_hint_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setSampleOutput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_so_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setSampleInput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_si_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setOutput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_output_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setInput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_input_title)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setDescription(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_input_description)
        )
            .setText(R.id.title_with_text_content, item.content)
    }

    private fun setTitle(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(R.id.item_problem_title_content, item.content)
    }

    override fun setNewData(oldData: MutableList<SingleProblemEntity>?) {
        val newData = ArrayList<SingleProblemEntity>()
        for (item in oldData as List<SingleProblemEntity>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                item.content = Html.fromHtml(item.content, 0, null, null).toString()
            }
            item.content = removeInvisibleChar(item.content)
            if (item.content != "") {
                newData.add(item)
            }
        }
        super.setNewData(newData)
    }

}