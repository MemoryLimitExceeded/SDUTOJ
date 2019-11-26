package com.sdutacm.sdutoj.mvp.base


interface IBaseContract {

    interface IBaseModel {
        fun requestData(args: Any?): Any?
        fun bind(presenter: IBasePresenter<out IBaseView>)
        fun dump()
        fun updateData(index: Any)
    }

    interface IBaseView {
        fun errorLoading()
        fun showLoading()
        fun hideLoading()
        fun hideErrorLoading()
        fun updateView(data: Any?)
        fun getModelFromView(): IBaseModel?
    }

    interface IBasePresenter<V : IBaseView> {
        fun attach(view: V)
        fun detach()
        fun getData(args: Any?): Any?
        fun requestDataError()
        fun requestDataWait()
        fun requestSuccess(data: Any?)
        fun updateData(index: Any)
    }

}