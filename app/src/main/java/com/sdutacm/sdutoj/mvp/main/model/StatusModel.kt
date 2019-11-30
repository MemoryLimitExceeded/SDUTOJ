package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import android.util.SparseArray
import com.sdutacm.sdutoj.LogUtils
import com.sdutacm.sdutoj.data.database.SolutionTable
import com.sdutacm.sdutoj.item.bean.StatusBean
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusModel : FragmentModel() {

    companion object {

        private val statusCache = SparseArray<StatusBean>()

        const val STATUS_INTERVAL = 50

        private const val QUERY_PARAMETERS_RUN_ID = "runid"

        private const val QUERY_PARAMETERS_UID = "uid"

        private const val QUERY_PARAMETERS_USER_NAME = "user_name"

        private const val QUERY_PARAMETERS_PID = "pid"

        private const val QUERY_PARAMETERS_CID = "cid"

        private const val QUERY_PARAMETERS_RESULT = "result"

        private const val QUERY_PARAMETERS_LANGUAGE = "language"

        @JvmStatic
        fun makeArgs(
            runid: Int? = null,
            uid: Int? = null,
            userName: String? = null,
            pid: Int? = null,
            cid: Int? = null,
            result: Int? = null,
            language: Int? = null,
            cmp: String? = null,
            order: String = CommonQueryParameters.ORDER_DESC.parameters,
            limit: Int = STATUS_INTERVAL
        ): Map<String, Any> {
            val args = makeArgs(cmp, order, limit)
            if (runid != null) {
                args[QUERY_PARAMETERS_RUN_ID] = runid
            }
            if (uid != null) {
                args[QUERY_PARAMETERS_UID] = uid
            }
            if (userName != null) {
                args[QUERY_PARAMETERS_USER_NAME] = userName
            }
            if (pid != null) {
                args[QUERY_PARAMETERS_PID] = pid
            }
            if (cid != null) {
                args[QUERY_PARAMETERS_CID] = cid
            }
            if (result != null) {
                args[QUERY_PARAMETERS_RESULT] = result
            }
            if (language != null) {
                args[QUERY_PARAMETERS_LANGUAGE] = language
            }
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int) {
        mService.getSolution(args).enqueue(object : Callback<List<StatusBean>> {
            override fun onFailure(call: Call<List<StatusBean>>, t: Throwable) {
                if (type == 1) {
                    requestDataFromDB(args, type)
                } else {
                    mPresenter?.requestDataError()
                }
            }

            override fun onResponse(
                call: Call<List<StatusBean>>,
                response: Response<List<StatusBean>>
            ) {
                val statusBeans = response.body()
                if (statusBeans != null) {
                    for (item in statusBeans) {
                        statusCache.put(item.runid, item)
                        updateDatabase(item)
                    }
                    requestSuccess(statusBeans, type)
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
        val data = ArrayList<StatusBean>()
        val length = args[QUERY_PARAMETERS_LIMIT] as Int
        if (args[QUERY_PARAMETERS_RUN_ID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_RUN_ID].toString())
            selection = addSelection(selection, SolutionTable.RUN_ID)
            selection = addCmdParameters((args[QUERY_PARAMETERS_CMD] as String), selection)
        }
        if (args[QUERY_PARAMETERS_UID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_UID].toString())
            selection = addSelection(selection, SolutionTable.UID + "=?")
        }
        if (args[QUERY_PARAMETERS_USER_NAME] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_USER_NAME].toString())
            selection = addSelection(selection, SolutionTable.USERNAME + "=?")
        }
        if (args[QUERY_PARAMETERS_PID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_PID].toString())
            selection = addSelection(selection, SolutionTable.PID + "=?")
        }
        if (args[QUERY_PARAMETERS_CID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_CID].toString())
            selection = addSelection(selection, SolutionTable.CID + "=?")
        }
        if (args[QUERY_PARAMETERS_RESULT] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_RESULT].toString())
            selection = addSelection(selection, SolutionTable.RESULT + "=?")
        }
        if (args[QUERY_PARAMETERS_LANGUAGE] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_LANGUAGE].toString())
            selection = addSelection(selection, SolutionTable.LANGUAGE + "=?")
        }
        if (args[QUERY_PARAMETERS_ORDER] != null) {
            order = SolutionTable.RUN_ID + " " + args[QUERY_PARAMETERS_ORDER].toString()
        }
        val cursor: Cursor = mDataBase.query(
            SolutionTable.TABLE_NAME,
            null,
            selection,
            toArray(selectionArgs),
            null,
            null,
            order,
            length.toString()
        )
        while (cursor.moveToNext()) {
            val item = StatusBean(
                cursor.getInt(cursor.getColumnIndex(SolutionTable.RUN_ID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.UID)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.USERNAME)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.PID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.CID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.RESULT)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.TIME)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.MEMORY)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.LANGUAGE)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.CODE_LENGTH)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.SUBMISSION_TIME))
            )
            statusCache.put(item.runid, item)
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
        contentValues.put(SolutionTable.RUN_ID, (data as StatusBean).runid)
        contentValues.put(SolutionTable.UID, data.uid)
        contentValues.put(SolutionTable.USERNAME, data.user_name)
        contentValues.put(SolutionTable.PID, data.pid)
        contentValues.put(SolutionTable.CID, data.cid)
        contentValues.put(SolutionTable.RESULT, data.result)
        contentValues.put(SolutionTable.TIME, data.time)
        contentValues.put(SolutionTable.MEMORY, data.memory)
        contentValues.put(SolutionTable.LANGUAGE, data.language)
        contentValues.put(SolutionTable.CODE_LENGTH, data.code_length)
        contentValues.put(SolutionTable.SUBMISSION_TIME, data.submission_time)
        if (mDataBase?.update(
                SolutionTable.TABLE_NAME,
                contentValues,
                SolutionTable.RUN_ID + "=?",
                arrayOf("${data.runid}")
            ) == 0
        ) {
            mDataBase.insert(SolutionTable.TABLE_NAME, null, contentValues)
        }
    }

}