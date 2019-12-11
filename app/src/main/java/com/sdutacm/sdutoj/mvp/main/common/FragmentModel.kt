package com.sdutacm.sdutoj.mvp.main.common

import android.database.sqlite.SQLiteDatabase
import com.sdutacm.sdutoj.data.database.SQLiteHelper
import com.sdutacm.sdutoj.data.network.INetApiService
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

        private val RETROFIT: Retrofit = Retrofit.Builder().baseUrl(NETWORK_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        private val DATABASE_HELPER = SQLiteHelper.getInstance()!!

        const val QUERY_REQUEST_MAX_LIMIT = 1000

        const val QUERY_PARAMETERS_CMD = "cmp"

        const val QUERY_PARAMETERS_ORDER = "order"

        const val QUERY_PARAMETERS_LIMIT = "limit"

        @JvmStatic
        fun makeArgs(cmp: String, order: String, limit: Int): HashMap<String, Any> {
            val args = HashMap<String, Any>()
            args[QUERY_PARAMETERS_CMD] = cmp
            args[QUERY_PARAMETERS_ORDER] = order
            args[QUERY_PARAMETERS_LIMIT] = limit
            return args
        }

    }

    protected val mService: INetApiService = RETROFIT.create(INetApiService::class.java)

    protected val mDataBase: SQLiteDatabase = DATABASE_HELPER.writableDatabase

    protected abstract fun updateDatabase(data: Any)

    protected abstract fun requestDataFromDB(args: HashMap<String, Any>)

    protected abstract fun requestDataFromNetWork(args: HashMap<String, Any>)

    @Suppress("UNCHECKED_CAST")
    override fun requestData(args: Any?) {
        requestDataFromNetWork(args as HashMap<String, Any>)
    }

    @Suppress("UNCHECKED_CAST")
    override fun requestMoreData(args: Any) {
        requestDataFromDB(args as HashMap<String, Any>)
    }

    protected fun requestSuccess(data: Any) {
        mPresenter?.requestSuccess(data)
    }

    protected fun toArray(selectionArgs: ArrayList<String>): Array<String> {
        val newSelectionArgs = Array(selectionArgs.size) { it.toString() }
        for (i in 0 until selectionArgs.size) {
            newSelectionArgs[i] = selectionArgs[i]
        }
        return newSelectionArgs
    }

    protected fun addCmdParameters(args: String, selection: String): String = when (args) {
        (CommonQueryParameters.CMP_EQUAL.parameters) -> "$selection=?"
        (CommonQueryParameters.CMP_NOT_EQUAL.parameters) -> "$selection!=?"
        (CommonQueryParameters.CMP_LESS.parameters) -> "$selection<?"
        (CommonQueryParameters.CMP_LESS_OR_EQUAL.parameters) -> "$selection<=?"
        (CommonQueryParameters.CMP_GREATER.parameters) -> "$selection>?"
        (CommonQueryParameters.CMP_GREATER_OR_EQUAL.parameters) ->
            "$selection>=?"
        else -> "$selection=?"
    }

    protected fun addSelection(selection: String, appendSelection: String): String {
        var newSelection = selection
        if (selection.isNotEmpty()) {
            newSelection = selection + " and "
        }
        return newSelection + appendSelection
    }

}