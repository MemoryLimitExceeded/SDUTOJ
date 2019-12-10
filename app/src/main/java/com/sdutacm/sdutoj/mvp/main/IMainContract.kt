package com.sdutacm.sdutoj.mvp.main

import com.sdutacm.sdutoj.mvp.base.IBaseContract

interface IMainContract {

    interface IMainModel : IBaseContract.IBaseModel {
        fun requestMoreData(args: Any)
    }

    interface IMainView : IBaseContract.IBaseView {
        override fun getModelFromView(): IMainModel?
        fun loadMoreData(data: Any)
    }

    interface IMainPresenter<V : IMainView> : IBaseContract.IBasePresenter<V> {
        fun getMoreData(args: Any)
    }

}