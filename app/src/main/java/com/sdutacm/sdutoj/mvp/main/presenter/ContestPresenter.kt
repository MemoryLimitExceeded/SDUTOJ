package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.model.ContestModel.Companion.makeArgs
import com.sdutacm.sdutoj.mvp.main.presenter.ContestPresenter.ContestDataHelper.Companion.CONTEST_LIST_TYPE

class ContestPresenter : FragmentPresenter() {

    override fun getData(args: Any?) {
        if (args is ContestDataHelper) {
            super.getData(getArgs(args))
        }
    }

    override fun getMoreData(args: Any) {
        if (args is ContestDataHelper) {
            super.getMoreData(getArgs(args))
        }
    }

    private fun getArgs(dataHelper: ContestDataHelper): Any {
        if (dataHelper.mRequestType == CONTEST_LIST_TYPE) {
            return makeArgs(
                dataHelper.mCid,
                dataHelper.mName,
                dataHelper.mType,
                dataHelper.mCmp,
                dataHelper.mOrder,
                dataHelper.mLimit
            )
        } else {
            return makeArgs(
                dataHelper.mCid!!,
                dataHelper.mUserName,
                dataHelper.mPassWord
            )
        }
    }

    class ContestDataHelper : DataHelper() {

        companion object {

            const val CONTEST_LIST_TYPE = 0

            const val CONTEST_ITEM_TYPE = 1

        }

        var mCid: Int? = null

        var mName: String? = null

        var mType: Int? = null

        var mUserName: String? = null

        var mPassWord: String? = null

        var mRequestType: Int = CONTEST_LIST_TYPE

        fun setCid(cid: Int): ContestDataHelper {
            this.mCid = cid
            return this
        }

        fun setName(name: String): ContestDataHelper {
            this.mName = name
            return this
        }

        fun setType(type: Int): ContestDataHelper {
            this.mType = type
            return this
        }

        fun setUserName(userName: String): ContestDataHelper {
            this.mUserName = userName
            return this
        }

        fun setPassWord(passWord: String): ContestDataHelper {
            this.mPassWord = passWord
            return this
        }

        fun setRequestType(requestType: Int): ContestDataHelper {
            this.mRequestType = requestType
            return this
        }

    }

}