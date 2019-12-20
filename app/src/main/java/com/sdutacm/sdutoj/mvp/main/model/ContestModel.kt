package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import com.sdutacm.sdutoj.data.database.ContestTable
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestModel : FragmentModel() {

    companion object {

        const val CONTEST_INTERVAL = 50

        private const val CONTEST_TYPE = 0

        private const val CONTEST_DETAIL_TYPE = 1

        private const val QUERY_PARAMETERS_REQUEST_TYPE = "request_type"

        private const val QUERY_PARAMETERS_CID = "cid"

        private const val QUERY_PARAMETERS_NAME = "name"

        private const val QUERY_PARAMETERS_TYPE = "type"

        private const val QUERY_PARAMETERS_USER_NAME = "user_name"

        private const val QUERY_PARAMETERS_PASSWORD = "password"

        @JvmStatic
        fun makeArgs(
            cid: Int?,
            name: String?,
            type: Int?,
            cmp: String,
            order: String,
            limit: Int,
            requestType: Int = CONTEST_TYPE
        ): Map<String, Any> {
            val args = makeArgs(cmp, order, limit)
            if (cid != null) {
                args[QUERY_PARAMETERS_CID] = cid
            }
            if (name != null) {
                args[QUERY_PARAMETERS_NAME] = name
            }
            if (type != null) {
                args[QUERY_PARAMETERS_TYPE] = type
            }
            args[QUERY_PARAMETERS_REQUEST_TYPE] = requestType
            return args
        }

        @JvmStatic
        fun makeArgs(
            cid: Int,
            userName: String?,
            password: String?,
            requestType: Int = CONTEST_DETAIL_TYPE
        ): Map<String, Any> {
            val args = makeArgs(
                CommonQueryParameters.CMP_EQUAL.parameters,
                CommonQueryParameters.ORDER_ASC.parameters,
                1
            )
            args[QUERY_PARAMETERS_CID] = cid
            if (userName != null)
                args[QUERY_PARAMETERS_USER_NAME] = userName
            if (password != null) {
                args[QUERY_PARAMETERS_PASSWORD] = password
            }
            args[QUERY_PARAMETERS_REQUEST_TYPE] = requestType
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>) {
        if (args[QUERY_PARAMETERS_REQUEST_TYPE] == CONTEST_TYPE) {
            mService.getContest(args).enqueue(object : Callback<List<ContestBean>> {
                override fun onFailure(call: Call<List<ContestBean>>, t: Throwable) {
                    mPresenter?.requestDataError(args)
                }

                override fun onResponse(
                    call: Call<List<ContestBean>>,
                    response: Response<List<ContestBean>>
                ) {
                    val contestBeans = response.body()
                    if (contestBeans != null && contestBeans.isNotEmpty()) {
                        for (item in contestBeans) {
                            updateDatabase(item)
                        }
                        requestSuccess(contestBeans)
                    } else {
                        requestSuccess(EMPTY_DATA)
                    }
                }
            })
        } else {
            mService.getContestDetail(args).enqueue(object : Callback<List<ContestBean>> {
                override fun onFailure(call: Call<List<ContestBean>>, t: Throwable) {
                    mPresenter?.requestDataError(args)
                }

                override fun onResponse(
                    call: Call<List<ContestBean>>,
                    response: Response<List<ContestBean>>
                ) {
                    val contestBeans = response.body()
                    if (contestBeans != null && contestBeans.isNotEmpty()) {
                        requestSuccess(contestBeans[0])
                    } else {
                        requestSuccess(EMPTY_DATA)
                    }
                }
            })
        }
    }

    override fun requestDataFromDB(args: HashMap<String, Any>) {
        var selection = ""
        val selectionArgs = ArrayList<String>()
        var order: String? = null
        val data = ArrayList<ContestBean>()
        val length = args[QUERY_PARAMETERS_LIMIT] as Int
        if (args[QUERY_PARAMETERS_CID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_CID].toString())
            selection = addSelection(selection, ContestTable.CID)
            selection = addCmdParameters((args[QUERY_PARAMETERS_CMP] as String), selection)
        }
        if (args[QUERY_PARAMETERS_NAME] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_NAME].toString())
            selection = addSelection(selection, ContestTable.NAME)
        }
        if (args[QUERY_PARAMETERS_TYPE] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_TYPE].toString())
            selection = addSelection(selection, ContestTable.TYPE)
        }
        if (args[QUERY_PARAMETERS_ORDER] != null) {
            order = ContestTable.CID + " " + args[QUERY_PARAMETERS_ORDER].toString()
        }
        val cursor: Cursor = mDataBase.query(
            ContestTable.TABLE_NAME,
            null,
            selection,
            toArray(selectionArgs),
            null,
            null,
            order,
            length.toString()
        )
        while (cursor.moveToNext()) {
            val item = ContestBean(
                cursor.getInt(cursor.getColumnIndex(ContestTable.CID)),
                cursor.getString(cursor.getColumnIndex(ContestTable.NAME)),
                cursor.getInt(cursor.getColumnIndex(ContestTable.TYPE)),
                cursor.getString(cursor.getColumnIndex(ContestTable.START_TIME)),
                cursor.getString(cursor.getColumnIndex(ContestTable.END_TIME)),
                cursor.getString(cursor.getColumnIndex(ContestTable.REGISTER_START_TIME)),
                cursor.getString(cursor.getColumnIndex(ContestTable.REGISTER_END_TIME))
            )
            data.add(item)
        }
        cursor.close()
        if (data.size == 0) {
            mPresenter?.requestDataError(null)
        } else {
            requestSuccess(data)
        }
    }

    override fun updateDatabase(data: Any) {
        val contentValues = ContentValues()
        contentValues.put(ContestTable.CID, (data as ContestBean).cid)
        contentValues.put(ContestTable.NAME, data.name)
        contentValues.put(ContestTable.TYPE, data.type)
        contentValues.put(ContestTable.START_TIME, data.start_time)
        contentValues.put(ContestTable.END_TIME, data.end_time)
        contentValues.put(ContestTable.REGISTER_START_TIME, data.register_start_time)
        contentValues.put(ContestTable.REGISTER_END_TIME, data.register_end_time)
        if (mDataBase.update(
                ContestTable.TABLE_NAME,
                contentValues,
                ContestTable.CID + "=?",
                arrayOf("${data.cid}")
            ) == 0
        ) {
            mDataBase.insert(ContestTable.TABLE_NAME, null, contentValues)
        }
    }

}