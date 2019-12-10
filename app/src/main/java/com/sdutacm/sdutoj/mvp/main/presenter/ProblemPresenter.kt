package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.makeArgs
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.MIN_PROBLEM_PID

class ProblemPresenter : FragmentPresenter() {

    override val dataHelper: ProblemDataHelper = ProblemDataHelper()

    override fun getData(args: Any?) {
        super.getData(
            makeArgs(
                dataHelper.pid,
                dataHelper.title,
                dataHelper.source,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    override fun getMoreData(args: Any) {
        super.getMoreData(
            makeArgs(
                dataHelper.pid,
                dataHelper.title,
                dataHelper.source,
                dataHelper.cmp,
                dataHelper.order,
                dataHelper.limit
            )
        )
    }

    class ProblemDataHelper : FragmentPresenter.DataHelper() {

        var pid = MIN_PROBLEM_PID

        var title: String? = null

        var source: String? = null

        fun setPid(pid: Int): ProblemDataHelper {
            this.pid = pid
            return this
        }

        fun setTitle(title: String): ProblemDataHelper {
            this.title = title
            return this
        }

        fun setSource(source: String): ProblemDataHelper {
            this.source = source
            return this
        }

    }

}
