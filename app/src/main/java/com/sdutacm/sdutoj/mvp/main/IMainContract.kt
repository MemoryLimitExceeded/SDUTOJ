package com.sdutacm.sdutoj.mvp.main

import com.sdutacm.sdutoj.mvp.base.IBaseContract

interface IMainContract {

    interface IMainModel : IBaseContract.IBaseModel {
    }

    interface IMainView : IBaseContract.IBaseView {
    }

    interface IMainPresenter<V : IMainView> : IBaseContract.IBasePresenter<V> {
    }

}