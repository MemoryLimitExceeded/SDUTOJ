package com.sdutacm.sdutoj.mvp.main.common

import com.sdutacm.sdutoj.data.database.SQLiteHelper
import com.sdutacm.sdutoj.data.network.INetApiService
import com.sdutacm.sdutoj.item.bean.BeanInterface
import com.sdutacm.sdutoj.mvp.base.BaseModel
import com.sdutacm.sdutoj.mvp.main.presenter.FragmentPresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class FragmentModel : BaseModel<FragmentPresenter>(),
    IMainContract.IMainModel {

    enum class CommonQueryParameters(val parameters: String) {
        CMP_EQUAL("e"),
        CMP_NOT_EQUAL("ne"),
        CMP_LESS("l"),
        CMP_LESS_OR_EQUAL("le"),
        CMP_GREATER("g"),
        CMP_GREATER_OR_EQUAL("ge"),
        ORDER_ASC("ASC"),
        ORDER_DESC("DESC")
    }

    companion object {

        private const val NETWORK_HOST_URL = "https://acm.sdut.edu.cn/onlinejudge2/index.php/API/"

        private val mRetrofit: Retrofit = Retrofit.Builder().baseUrl(NETWORK_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        private val mDataBaseHelper = SQLiteHelper.getInstance()

        const val mQueryParametersCmd = "cmp"

        const val mQueryParametersOrder = "order"

        const val mQueryParametersLimit = "limit"

        @JvmStatic
        fun makeArgs(cmp: String?, order: String?, limit: Int): HashMap<String, Any> {
            val args = HashMap<String, Any>()
            if (cmp != null) {
                args[mQueryParametersCmd] = cmp
            }
            if (order != null) {
                args[mQueryParametersOrder] = order
            }
            args[mQueryParametersLimit] = limit
            return args
        }

    }

    protected val mService: INetApiService = mRetrofit.create(INetApiService::class.java)

    protected val mDataBase = mDataBaseHelper?.writableDatabase

    protected abstract fun updateDatabase(data: Any)

    protected abstract fun requestDataFromDB(args: HashMap<String, Any>, type: Int)

    protected abstract fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int)

    @Suppress("UNCHECKED_CAST")
    override fun requestMoreData(args: Any) {
        requestDataFromNetWork(args as HashMap<String, Any>, 1)
    }

    @Suppress("UNCHECKED_CAST")
    override fun requestData(args: Any?) {
        requestDataFromNetWork(args as HashMap<String, Any>, 0)
    }

    protected fun requestSuccess(data: List<BeanInterface>, type: Int) {
        if (type == 0) {
            mPresenter?.requestSuccess(data)
        } else {
            mPresenter?.requestMoreDataSuccess(data)
        }
    }

}