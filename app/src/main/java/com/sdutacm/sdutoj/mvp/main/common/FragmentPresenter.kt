package com.sdutacm.sdutoj.mvp.main.common

import com.sdutacm.sdutoj.mvp.base.BasePresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel

abstract class FragmentPresenter() : BasePresenter<IMainContract.IMainView, FragmentModel>(),
    IMainContract.IMainPresenter<IMainContract.IMainView> {

    override fun initModel() {
        val model: FragmentModel? = mView?.getModelFromView() as FragmentModel?
        if (model == null) {
            mModel = ProblemModel()
        } else {
            mModel = model
        }
    }

}