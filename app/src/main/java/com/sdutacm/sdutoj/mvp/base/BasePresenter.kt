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

    override fun getData(args: Any?): Any? {
        return mModel?.requestData(args)
    }

    override fun requestSuccess(data: Any?) {
        mView?.updateView(data)
    }

    override fun requestDataWait() {
        mView?.showLoading()
    }

    override fun requestDataError() {
        mView?.errorLoading()
    }

    override fun updateData(index: Any) {
        mModel?.updateData(index)
    }

}