package com.sdutacm.sdutoj.mvp.main.common

import com.sdutacm.sdutoj.mvp.base.BaseModel
import com.sdutacm.sdutoj.mvp.main.IMainContract

abstract class FragmentModel : BaseModel<FragmentPresenter>(),
    IMainContract.IMainModel {

    protected abstract fun requestDataFromDB()

    protected abstract fun requestDataFromNetWork()

}