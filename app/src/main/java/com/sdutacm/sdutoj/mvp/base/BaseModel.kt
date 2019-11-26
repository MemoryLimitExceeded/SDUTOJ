package com.sdutacm.sdutoj.mvp.base

import com.sdutacm.sdutoj.data.database.SQLiteHelper
import com.sdutacm.sdutoj.data.network.INetApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseModel<P : BasePresenter<out IBaseContract.IBaseView, out IBaseContract.IBaseModel>> :
    IBaseContract.IBaseModel {

    companion object {

        private const val NETWORK_HOST_URL = "https://acm.sdut.edu.cn/onlinejudge2/index.php/API/"

        private val mRetrofit: Retrofit = Retrofit.Builder().baseUrl(NETWORK_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        private val mDataBaseHelper = SQLiteHelper.getInstance()

    }

    protected val mService: INetApiService = mRetrofit.create(INetApiService::class.java)

    protected val mDataBase = mDataBaseHelper?.writableDatabase

    protected var mPresenter: P? = null

    protected abstract fun updateDatabase(data: Any)

    @Suppress("UNCHECKED_CAST")
    override fun bind(presenter: IBaseContract.IBasePresenter<out IBaseContract.IBaseView>) {
        mPresenter = presenter as P
    }

    override fun dump() {
        mPresenter = null
    }

}