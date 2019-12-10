package com.sdutacm.sdutoj.mvp.base

abstract class BasePresenter<V : IBaseContract.IBaseView, M : IBaseContract.IBaseModel> :
    IBaseContract.IBasePresenter<V> {

    protected var mView: V? = null

    protected var mModel: M? = null

    protected abstract fun initModel()

    override fun attach(view: V) {
        mView = view
        initModel()
        mModel?.bind(this)
    }

    override fun detach() {
        mView = null
        mModel?.dump()
        mModel = null
    }

    override fun getData(args: Any?) {
        mModel?.requestData(args)
    }

    override fun requestSuccess(data: Any) {
        mView?.updateData(data)
    }

    override fun requestDataError(args: Any?) {
        mView?.errorLoading()
    }

}