package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.ContestModel.Companion.makeArgs

class ContestPresenter : FragmentPresenter() {

    override val dataHelper: ContentDataHelper = ContentDataHelper()

    override fun getData(args: Any?) {
        super.getData(
            makeArgs(
                dataHelper.cid,
                dataHelper.name,
                dataHelper.type,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    override fun getMoreData(args: Any) {
        super.getMoreData(
            makeArgs(
                dataHelper.cid,
                dataHelper.name,
                dataHelper.type,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    class ContentDataHelper : DataHelper() {

        var cid: Int? = null

        var name: String? = null

        var type: Int? = null

        fun setCid(cid: Int): ContentDataHelper {
            this.cid = cid
            return this
        }

        fun setName(name: String): ContentDataHelper {
            this.name = name
            return this
        }

        fun setType(type: Int): ContentDataHelper {
            this.type = type
            return this
        }

    }

}