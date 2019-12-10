package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.StatusModel.Companion.makeArgs

class StatusPresenter : FragmentPresenter() {

    override val dataHelper: StatusDataHelper = StatusDataHelper()

    override fun getData(args: Any?) {
        super.getData(
            makeArgs(
                dataHelper.runid,
                dataHelper.uid,
                dataHelper.userName,
                dataHelper.pid,
                dataHelper.cid,
                dataHelper.result,
                dataHelper.language,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    override fun getMoreData(args: Any) {
        super.getMoreData(
            makeArgs(
                dataHelper.runid,
                dataHelper.uid,
                dataHelper.userName,
                dataHelper.pid,
                dataHelper.cid,
                dataHelper.result,
                dataHelper.language,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    class StatusDataHelper : DataHelper() {

        var runid: Int? = null

        var uid: Int? = null

        var userName: String? = null

        var pid: Int? = null

        var cid: Int? = null

        var result: Int? = null

        var language: Int? = null

        fun setRunId(runid: Int): StatusDataHelper {
            this.runid = runid
            return this
        }

        fun setUId(uid: Int): StatusDataHelper {
            this.uid = uid
            return this
        }

        fun setUserName(userName: String): StatusDataHelper {
            this.userName = userName
            return this
        }

        fun setPId(pid: Int): StatusDataHelper {
            this.pid = pid
            return this
        }

        fun setCId(cid: Int): StatusDataHelper {
            this.cid = cid
            return this
        }

        fun setResult(result: Int): StatusDataHelper {
            this.result = result
            return this
        }

        fun setLanguage(language: Int): StatusDataHelper {
            this.language = language
            return this
        }

    }

}