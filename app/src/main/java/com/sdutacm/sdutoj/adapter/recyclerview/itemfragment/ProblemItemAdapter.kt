package com.sdutacm.sdutoj.adapter.recyclerview.itemfragment

import com.sdutacm.sdutoj.QuickViewHolder
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.common.ListAdapter
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.TITLE_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.DESCRIPTION_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.HINT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SOURCE_TYPE

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
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setHint(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_hint_title)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setSampleOutput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_so_title)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setSampleInput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_si_title)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setOutput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_output_title)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setInput(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_input_title)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setDescription(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(
            R.id.title_with_text_title,
            getResString(R.string.problem_item_fragment_input_description)
        )
            .setText(R.id.title_with_text_content, item.mListContent[0])
    }

    private fun setTitle(helper: QuickViewHolder, item: SingleProblemEntity) {
        helper.setText(R.id.item_problem_title_content, item.mListContent[0])
            .setText(
                R.id.item_problem_title_tl,
                getResString(R.string.problem_item_fragment_title_tl).format(item.mListContent[1])
            )
            .setText(
                R.id.item_problem_title_ml,
                getResString(R.string.problem_item_fragment_title_ml).format(item.mListContent[2])
            )
            .setText(R.id.item_problem_title_ac_content, item.mListContent[3])
            .setText(R.id.item_problem_title_su_content, item.mListContent[4])
    }

    override fun setNewData(oldData: MutableList<SingleProblemEntity>?) {
        val newData = ArrayList<SingleProblemEntity>()
        for (item in oldData as List<SingleProblemEntity>) {
            var flag = 0
            for (i in 0 until item.mListContent.size) {
                var content = item.mListContent[i]
                content = htmlToText(content)
                content = removeInvisibleChar(content)
                item.mListContent[i] = content
                if (content != "") {
                    flag = 1
                }
            }
            if (flag == 1) {
                newData.add(item)
            }
        }
        super.setNewData(newData)
    }

}