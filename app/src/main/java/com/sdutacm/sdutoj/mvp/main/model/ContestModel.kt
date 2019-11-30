package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import android.util.SparseArray
import com.sdutacm.sdutoj.data.database.ContestTable
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestModel : FragmentModel() {

    companion object {

        private val contestCache = SparseArray<ContestBean>()

        const val CONTEST_INTERVAL = 50

        private const val QUERY_PARAMETERS_CID = "cid"

        private const val QUERY_PARAMETERS_NAME = "name"

        private const val QUERY_PARAMETERS_TYPE = "type"

        @JvmStatic
        fun makeArgs(
            cid: Int? = null,
            name: String? = null,
            type: Int? = null,
            cmp: String? = null,
            order: String = CommonQueryParameters.ORDER_DESC.parameters,
            limit: Int = CONTEST_INTERVAL
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
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int) {
        mService.getContest(args).enqueue(object : Callback<List<ContestBean>> {
            override fun onFailure(call: Call<List<ContestBean>>, t: Throwable) {
                if (type == 1) {
                    requestDataFromDB(args, type)
                } else {
                    mPresenter?.requestDataError()
                }
            }

            override fun onResponse(
                call: Call<List<ContestBean>>,
                response: Response<List<ContestBean>>
            ) {
                val contestBeans = response.body()
                if (contestBeans != null) {
                    for (item in contestBeans) {
                        contestCache.put(item.cid, item)
                        updateDatabase(item)
                    }
                    requestSuccess(contestBeans, type)
                } else {
                    requestDataFromDB(args, type)
                }
            }
        })
    }

    override fun requestDataFromDB(args: HashMap<String, Any>, type: Int) {
        var selection = ""
        val selectionArgs = ArrayList<String>()
        var order: String? = null
        val data = ArrayList<ContestBean>()
        val length = args[QUERY_PARAMETERS_LIMIT] as Int
        if (args[QUERY_PARAMETERS_CID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_CID].toString())
            selection = addSelection(selection, ContestTable.CID)
            selection = addCmdParameters((args[QUERY_PARAMETERS_CMD] as String), selection)
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
            contestCache.put(item.cid, item)
            data.add(item)
        }
        cursor.close()
        if (data.size == 0) {
            mPresenter?.requestDataError()
        } else {
            requestSuccess(data, type)
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
        if (mDataBase?.update(
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