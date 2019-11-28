package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.base.BasePresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel

open class FragmentPresenter() : BasePresenter<IMainContract.IMainView, FragmentModel>(),
    IMainContract.IMainPresenter<IMainContract.IMainView> {

    override fun initModel() {
        val model: FragmentModel? = mView?.getModelFromView() as FragmentModel?
        mModel = model
    }

    override fun getMoreData(args: Any) {
        mModel?.requestMoreData(args)
    }

    override fun requestMoreDataSuccess(data: Any) {
        mView?.loadMoreData(data)
    }

}