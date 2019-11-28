package com.sdutacm.sdutoj.mvp.base


interface IBaseContract {

    interface IBaseModel {
        fun requestData(args: Any?)
        fun bind(presenter: IBasePresenter<out IBaseView>)
        fun dump()
    }

    interface IBaseView {
        fun errorLoading()
        fun showLoading()
        fun hideLoading()
        fun updateData(data: Any?)
        fun getModelFromView(): IBaseModel?
    }

    interface IBasePresenter<V : IBaseView> {
        fun attach(view: V)
        fun detach()
        fun getData(args: Any?)
        fun requestDataError()
        fun requestSuccess(data: Any)
    }

}