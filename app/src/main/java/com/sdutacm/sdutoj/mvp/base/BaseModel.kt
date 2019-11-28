package com.sdutacm.sdutoj.mvp.base

abstract class BaseModel<P : BasePresenter<out IBaseContract.IBaseView, out IBaseContract.IBaseModel>> :
    IBaseContract.IBaseModel {

    protected var mPresenter: P? = null

    @Suppress("UNCHECKED_CAST")
    override fun bind(presenter: IBaseContract.IBasePresenter<out IBaseContract.IBaseView>) {
        mPresenter = presenter as P
    }

    override fun dump() {
        mPresenter = null
    }

}