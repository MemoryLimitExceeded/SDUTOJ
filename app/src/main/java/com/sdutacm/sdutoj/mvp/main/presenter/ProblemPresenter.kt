package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.common.FragmentPresenter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.problemRequestStartPid

class ProblemPresenter : FragmentPresenter() {

    private val mInterval = 19

    private var mLastRequestIndex = problemRequestStartPid

    private var mHidingProblemCount = 0

    private var mCurrentDataSize = 0

    private var mData: Array<ProblemBean?> = arrayOfNulls(mInterval + 1)

    override fun getData(args: Any?): Array<ProblemBean?>? {
        val index = args as Int
        for (i in index..index + mInterval) {
            val data = super.getData(i)
            if (data is ProblemBean) {
                mCurrentDataSize++
                mData[data.pid - index] = data
            }
        }
        if (mCurrentDataSize != mInterval + 1 - mHidingProblemCount) {
            return null
        }
        val newArray = mData
        initDataStatus()
        return newArray
    }

    override fun requestSuccess(data: Any?) {
        if (data is ProblemBean) {
            if (data.pid < mLastRequestIndex) {
                super.requestSuccess(data)
                return
            }
            if (checkDataExisted(data)) {
                return
            }
            mData[data.pid - mLastRequestIndex] = data
            mCurrentDataSize++
            sendDataToView()
            return
        }
        mHidingProblemCount++
        sendDataToView()
    }

    private fun sendDataToView() {
        if (mInterval + 1 - mHidingProblemCount == mCurrentDataSize) {
            val newArray = mData
            initDataStatus()
            super.requestSuccess(newArray)
        }
    }

    private fun checkDataExisted(data: ProblemBean): Boolean {
        for (i in 0..mInterval) {
            val item = mData[i]
            if (item != null && item.pid == data.pid) {
                mData[i] = data
                return true
            }
        }
        return false
    }

    private fun initDataStatus() {
        mData = arrayOfNulls(mInterval + 1)
        mHidingProblemCount = 0
        mLastRequestIndex = mLastRequestIndex + mInterval + 1
        mCurrentDataSize = 0
    }

    fun getInterval(): Int {
        return mInterval
    }

}