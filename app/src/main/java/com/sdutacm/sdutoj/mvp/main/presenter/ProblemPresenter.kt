package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.makeArgs
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.MIN_PROBLEM_PID

class ProblemPresenter : FragmentPresenter() {

    override fun getData(args: Any?) {
        if (args is ProblemDataHelper) {
            super.getData(
                makeArgs(
                    args.mPid,
                    args.mTitle,
                    args.mSource,
                    args.mCmp,
                    args.mOrder,
                    args.mLimit
                )
            )
        }
    }

    override fun getMoreData(args: Any) {
        if (args is ProblemDataHelper) {
            super.getMoreData(
                makeArgs(
                    args.mPid,
                    args.mTitle,
                    args.mSource,
                    args.mCmp,
                    args.mOrder,
                    args.mLimit
                )
            )
        }
    }

    class ProblemDataHelper : FragmentPresenter.DataHelper() {

        var mPid = MIN_PROBLEM_PID

        var mTitle: String? = null

        var mSource: String? = null

        fun setPid(pid: Int): ProblemDataHelper {
            this.mPid = pid
            return this
        }

        fun setTitle(title: String): ProblemDataHelper {
            this.mTitle = title
            return this
        }

        fun setSource(source: String): ProblemDataHelper {
            this.mSource = source
            return this
        }

    }

}
