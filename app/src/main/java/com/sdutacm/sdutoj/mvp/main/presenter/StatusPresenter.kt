package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.StatusModel.Companion.makeArgs

class StatusPresenter : FragmentPresenter() {

    override fun getData(args: Any?) {
        if (args is StatusDataHelper) {
            super.getData(
                makeArgs(
                    args.mRunid,
                    args.mUid,
                    args.mUserName,
                    args.mPid,
                    args.mCid,
                    args.mResult,
                    args.mLanguage,
                    args.mCmp,
                    args.mOrder,
                    args.mLimit
                )
            )
        }
    }

    override fun getMoreData(args: Any) {
        if (args is StatusDataHelper) {
            super.getMoreData(
                makeArgs(
                    args.mRunid,
                    args.mUid,
                    args.mUserName,
                    args.mPid,
                    args.mCid,
                    args.mResult,
                    args.mLanguage,
                    args.mCmp,
                    args.mOrder,
                    args.mLimit
                )
            )
        }
    }

    class StatusDataHelper : DataHelper() {

        var mRunid: Int? = null

        var mUid: Int? = null

        var mUserName: String? = null

        var mPid: Int? = null

        var mCid: Int? = null

        var mResult: Int? = null

        var mLanguage: Int? = null

        fun setRunId(runid: Int): StatusDataHelper {
            this.mRunid = runid
            return this
        }

        fun setUId(uid: Int): StatusDataHelper {
            this.mUid = uid
            return this
        }

        fun setUserName(userName: String): StatusDataHelper {
            this.mUserName = userName
            return this
        }

        fun setPId(pid: Int): StatusDataHelper {
            this.mPid = pid
            return this
        }

        fun setCId(cid: Int): StatusDataHelper {
            this.mCid = cid
            return this
        }

        fun setResult(result: Int): StatusDataHelper {
            this.mResult = result
            return this
        }

        fun setLanguage(language: Int): StatusDataHelper {
            this.mLanguage = language
            return this
        }

    }

}