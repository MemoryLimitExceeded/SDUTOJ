package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.base.BasePresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ActivityModel
import com.sdutacm.sdutoj.ui.activity.MainActivity

class ActivityPresenter : BasePresenter<MainActivity, ActivityModel>(),
    IMainContract.IMainPresenter<MainActivity> {

    override fun initModel() {
        val model: ActivityModel? = mView?.getModelFromView() as ActivityModel?
        if (model == null) {
            mModel = ActivityModel()
        } else {
            mModel = model
        }
    }

    override fun getMoreData(args: Any) {
        mModel?.requestMoreData(args)
    }

}