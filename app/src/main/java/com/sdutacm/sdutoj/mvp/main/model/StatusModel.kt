package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import android.util.SparseArray
import com.sdutacm.sdutoj.data.database.SolutionTable
import com.sdutacm.sdutoj.item.bean.StatusBean
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusModel : FragmentModel() {

    companion object {

        private val statusCache = SparseArray<StatusBean>()

        const val mStatusInterval = 50

        private const val mQueryParametersRunId = "runid"

        private const val mQueryParametersUId = "uid"

        private const val mQueryParametersUserName = "user_name"

        private const val mQueryParametersPId = "pid"

        private const val mQueryParametersCId = "cid"

        private const val mQueryParametersResult = "result"

        private const val mQueryParametersLanguage = "language"

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
            limit: Int = mStatusInterval
        ): Map<String, Any> {
            val args = makeArgs(cmp, order, limit)
            if (runid != null) {
                args[mQueryParametersRunId] = runid
            }
            if (uid != null) {
                args[mQueryParametersUId] = uid
            }
            if (userName != null) {
                args[mQueryParametersUserName] = userName
            }
            if (pid != null) {
                args[mQueryParametersPId] = pid
            }
            if (cid != null) {
                args[mQueryParametersCId] = cid
            }
            if (result != null) {
                args[mQueryParametersResult] = result
            }
            if (language != null) {
                args[mQueryParametersLanguage] = language
            }
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int) {
        mService.getSolution(args).enqueue(object : Callback<List<StatusBean>> {
            override fun onFailure(call: Call<List<StatusBean>>, t: Throwable) {
                com.sdutacm.sdutoj.utils.LogUtils.d("Solution request fail : $t")
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
                        com.sdutacm.sdutoj.utils.LogUtils.d("Solution request successful : ${item.runid}")
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
        val length = args[mQueryParametersLimit] as Int
        if (args[mQueryParametersRunId] != null) {
            selectionArgs.add(args[mQueryParametersRunId].toString())
            selection = addSelection(selection, SolutionTable.RUNID)
            selection = addCmdParameters((args[mQueryParametersCmd] as String), selection)
        }
        if (args[mQueryParametersUId] != null) {
            selectionArgs.add(args[mQueryParametersUId].toString())
            selection = addSelection(selection, SolutionTable.UID + "=?")
        }
        if (args[mQueryParametersUserName] != null) {
            selectionArgs.add(args[mQueryParametersUserName].toString())
            selection = addSelection(selection, SolutionTable.USERNAME + "=?")
        }
        if (args[mQueryParametersPId] != null) {
            selectionArgs.add(args[mQueryParametersPId].toString())
            selection = addSelection(selection, SolutionTable.PID + "=?")
        }
        if (args[mQueryParametersCId] != null) {
            selectionArgs.add(args[mQueryParametersCId].toString())
            selection = addSelection(selection, SolutionTable.CID + "=?")
        }
        if (args[mQueryParametersResult] != null) {
            selectionArgs.add(args[mQueryParametersResult].toString())
            selection = addSelection(selection, SolutionTable.RESULT + "=?")
        }
        if (args[mQueryParametersLanguage] != null) {
            selectionArgs.add(args[mQueryParametersLanguage].toString())
            selection = addSelection(selection, SolutionTable.LANGUAGE + "=?")
        }
        if (args[mQueryParametersOrder] != null) {
            order = SolutionTable.RUNID + " " + args[mQueryParametersOrder].toString()
        }
        val cursor: Cursor = mDataBase.query(
            SolutionTable.TABLENAME,
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
                cursor.getInt(cursor.getColumnIndex(SolutionTable.RUNID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.UID)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.USERNAME)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.PID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.CID)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.RESULT)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.TIME)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.MEMORY)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.LANGUAGE)),
                cursor.getInt(cursor.getColumnIndex(SolutionTable.CODELENGTH)),
                cursor.getString(cursor.getColumnIndex(SolutionTable.SUBMISSIONTIME))
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
        contentValues.put(SolutionTable.RUNID, (data as StatusBean).runid)
        contentValues.put(SolutionTable.UID, data.uid)
        contentValues.put(SolutionTable.USERNAME, data.user_name)
        contentValues.put(SolutionTable.PID, data.pid)
        contentValues.put(SolutionTable.CID, data.cid)
        contentValues.put(SolutionTable.RESULT, data.result)
        contentValues.put(SolutionTable.TIME, data.time)
        contentValues.put(SolutionTable.MEMORY, data.memory)
        contentValues.put(SolutionTable.LANGUAGE, data.language)
        contentValues.put(SolutionTable.CODELENGTH, data.code_length)
        contentValues.put(SolutionTable.SUBMISSIONTIME, data.submission_time)
        if (mDataBase?.update(
                SolutionTable.TABLENAME,
                contentValues,
                SolutionTable.RUNID + "=?",
                arrayOf("${data.runid}")
            ) == 0
        ) {
            mDataBase.insert(SolutionTable.TABLENAME, null, contentValues)
        }
    }

}