package com.sdutacm.sdutoj.item.entity.itemfragment

import com.sdutacm.sdutoj.item.entity.common.ListItemEntity

class SingleProblemEntity(var mListContent: ArrayList<String>, itemType: Int) : ListItemEntity(itemType) {

    companion object {

        const val TITLE_TYPE = 1

        const val DESCRIPTION_TYPE = 2

        const val INPUT_TYPE = 3

        const val OUTPUT_TYPE = 4

        const val SAMPLE_INPUT_TYPE = 5

        const val SAMPLE_OUTPUT_TYPE = 6

        const val HINT_TYPE = 7

        const val SOURCE_TYPE = 8

    }

}