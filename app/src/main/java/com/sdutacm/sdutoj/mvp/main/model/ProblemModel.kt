package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import android.util.SparseArray
import com.sdutacm.sdutoj.LogUtils
import com.sdutacm.sdutoj.data.database.ProblemTable
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.item.bean.ProblemBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProblemModel : FragmentModel() {

    companion object {

        private val problemCache = SparseArray<ProblemBean>()

        private const val QUERY_PARAMETERS_PID = "pid"

        private const val QUERY_PARAMETERS_TITLE = "title"

        private const val QUERY_PARAMETERS_SOURCE = "source"

        const val PROBLEM_INTERVAL = 50

        const val MIN_PROBLEM_PID = 1000

        @JvmStatic
        fun makeArgs(
            pid: Int = MIN_PROBLEM_PID,
            title: String? = null,
            source: String? = null,
            cmp: String? = null,
            order: String = CommonQueryParameters.ORDER_DESC.parameters,
            limit: Int = PROBLEM_INTERVAL
        ): Map<String, Any> {
            val args = makeArgs(cmp, order, limit)
            args[QUERY_PARAMETERS_PID] = pid
            if (title != null) {
                args[QUERY_PARAMETERS_TITLE] = title
            }
            if (source != null) {
                args[QUERY_PARAMETERS_SOURCE] = source
            }
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int) {
        mService.getProblem(args).enqueue(object : Callback<List<ProblemBean>> {
            override fun onFailure(call: Call<List<ProblemBean>>, t: Throwable) {
                if (type == 1) {
                    requestDataFromDB(args, type)
                } else {
                    mPresenter?.requestDataError()
                }
            }

            override fun onResponse(
                call: Call<List<ProblemBean>>,
                response: Response<List<ProblemBean>>
            ) {
                val problemBeans = response.body()
                if (problemBeans != null) {
                    for (item in problemBeans) {
                        problemCache.put(item.pid, item)
                        updateDatabase(item)
                    }
                    requestSuccess(problemBeans, type)
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
        val data = ArrayList<ProblemBean>()
        val length = args[QUERY_PARAMETERS_LIMIT] as Int
        if (args[QUERY_PARAMETERS_PID] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_PID].toString())
            selection = addSelection(selection, ProblemTable.PID)
            selection = addCmdParameters((args[QUERY_PARAMETERS_CMD] as String), selection)
        }
        if (args[QUERY_PARAMETERS_TITLE] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_TITLE].toString())
            selection = addSelection(selection, ProblemTable.TITLE + " like %?%")
        }
        if (args[QUERY_PARAMETERS_SOURCE] != null) {
            selectionArgs.add(args[QUERY_PARAMETERS_SOURCE].toString())
            selection = addSelection(selection, ProblemTable.SOURCE + " like %?%")
        }
        if (args[QUERY_PARAMETERS_ORDER] != null) {
            order = ProblemTable.PID + " " + args[QUERY_PARAMETERS_ORDER].toString()
        }
        val cursor: Cursor = mDataBase.query(
            ProblemTable.TABLE_NAME,
            null,
            selection,
            toArray(selectionArgs),
            null,
            null,
            order,
            length.toString()
        )
        while (cursor.moveToNext()) {
            val item = ProblemBean(
                cursor.getInt(cursor.getColumnIndex(ProblemTable.PID)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.TITLE)),
                cursor.getInt(cursor.getColumnIndex(ProblemTable.TIME_LIMIT)),
                cursor.getInt(cursor.getColumnIndex(ProblemTable.MEMORY_LIMIT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.INPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.OUTPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SAMPLE_INPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SAMPLE_OUTPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.HINT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SOURCE)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.ADDED_TIME)),
                cursor.getInt(cursor.getColumnIndex(ProblemTable.ACCEPTED)),
                cursor.getInt(cursor.getColumnIndex(ProblemTable.SUBMISSION))
            )
            problemCache.put(item.pid, item)
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
        contentValues.put(ProblemTable.PID, (data as ProblemBean).pid)
        contentValues.put(ProblemTable.TITLE, data.title)
        contentValues.put(ProblemTable.TIME_LIMIT, data.time_limit)
        contentValues.put(ProblemTable.MEMORY_LIMIT, data.memory_limit)
        contentValues.put(ProblemTable.DESCRIPTION, data.description)
        contentValues.put(ProblemTable.INPUT, data.input)
        contentValues.put(ProblemTable.OUTPUT, data.output)
        contentValues.put(ProblemTable.SAMPLE_INPUT, data.sample_input)
        contentValues.put(ProblemTable.SAMPLE_OUTPUT, data.sample_output)
        contentValues.put(ProblemTable.HINT, data.hint)
        contentValues.put(ProblemTable.SOURCE, data.source)
        contentValues.put(ProblemTable.ADDED_TIME, data.added_time)
        contentValues.put(ProblemTable.ACCEPTED, data.accepted)
        contentValues.put(ProblemTable.SUBMISSION, data.submission)
        if (mDataBase?.update(
                ProblemTable.TABLE_NAME,
                contentValues,
                ProblemTable.PID + "=?",
                arrayOf("${data.pid}")
            ) == 0
        ) {
            mDataBase.insert(ProblemTable.TABLE_NAME, null, contentValues)
        }
    }

}