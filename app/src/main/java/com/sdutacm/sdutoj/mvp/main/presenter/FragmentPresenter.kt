package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.base.BasePresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel

open class FragmentPresenter() : BasePresenter<IMainContract.IMainView, FragmentModel>(),
    IMainContract.IMainPresenter<IMainContract.IMainView> {

    protected var mType: Int = 0

    open val dataHelper: DataHelper = DataHelper()

    companion object {

        protected const val TYPE_GET_DATA_ID = 0

        protected const val TYPE_MORE_DATA_ID = 1

    }

    override fun initModel() {
        val model: FragmentModel? = mView?.getModelFromView() as FragmentModel?
        mModel = model
    }

    override fun getData(args: Any?) {
        mType = TYPE_GET_DATA_ID
        super.getData(args)
    }

    override fun getMoreData(args: Any) {
        mType = TYPE_MORE_DATA_ID
        super.getData(args)
    }

    override fun requestSuccess(data: Any) {
        if (mType == TYPE_GET_DATA_ID) {
            super.requestSuccess(data)
        } else {
            mView?.loadMoreData(data)
        }
    }

    override fun requestDataError(args: Any?) {
        if (mType == TYPE_GET_DATA_ID) {
            super.requestDataError(args)
        } else {
            if (args != null) {
                mModel?.requestMoreData(args)
            } else {
                super.requestDataError(args)
            }
        }
    }

    open class DataHelper {

        var cmp: String? = null

        var order: String = FragmentModel.CommonQueryParameters.ORDER_DESC.parameters

        var limit: Int = 1

        fun setCmp(cmp: String): DataHelper {
            this.cmp = cmp
            return this
        }

        fun setOrder(order: String): DataHelper {
            this.order = order
            return this
        }

        fun setLimit(limit: Int): DataHelper {
            this.limit = limit
            return this
        }

    }

}