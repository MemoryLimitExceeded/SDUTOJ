package com.sdutacm.sdutoj.adapter.recyclerview.itemfragment

import android.os.Build
import android.text.Html
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
        addItemType(TITLE_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(DESCRIPTION_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(INPUT_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(OUTPUT_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(SAMPLE_INPUT_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(SAMPLE_OUTPUT_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(HINT_TYPE, R.layout.itemlayout_textview_with_cardview)
        addItemType(SOURCE_TYPE, R.layout.itemlayout_textview_with_cardview)
    }

    override fun convert(helper: QuickViewHolder, item: SingleProblemEntity) {
        var content = item.content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content = Html.fromHtml(content, 0, null, null).toString()
        }
        content = removeInvisibleChar(content)
        if (content == "") {
            helper.setGone(R.id.rootview_cardview, false)
        } else {
            helper.setGone(R.id.rootview_cardview, true)
            helper.setText(R.id.textview_in_cardview, content)
        }
    }

}