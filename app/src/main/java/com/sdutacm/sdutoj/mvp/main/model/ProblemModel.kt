package com.sdutacm.sdutoj.mvp.main.model

import android.content.ContentValues
import android.database.Cursor
import android.util.SparseArray
import com.sdutacm.sdutoj.data.database.ProblemTable
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.item.bean.ProblemBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProblemModel : FragmentModel() {

    companion object {

        private val problemCache = SparseArray<ProblemBean>()

        private const val mQueryParametersPId = "pid"

        private const val mQueryParametersTitle = "title"

        private const val mQueryParametersSource = "source"

        const val mProblemInterval = 50

        const val mMinProblemPid = 1000

        @JvmStatic
        fun makeArgs(
            pid: Int = mMinProblemPid,
            title: String? = null,
            source: String? = null,
            cmp: String? = null,
            order: String = CommonQueryParameters.ORDER_DESC.parameters,
            limit: Int = mProblemInterval
        ): Map<String, Any> {
            val args = makeArgs(cmp, order, limit)
            args[mQueryParametersPId] = pid
            if (title != null) {
                args[mQueryParametersTitle] = title
            }
            if (source != null) {
                args[mQueryParametersSource] = source
            }
            return args
        }

    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>, type: Int) {
        mService.getProblem(args).enqueue(object : Callback<List<ProblemBean>> {
            override fun onFailure(call: Call<List<ProblemBean>>, t: Throwable) {
                com.sdutacm.sdutoj.utils.LogUtils.e("Problem request fail : $t")
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
                        com.sdutacm.sdutoj.utils.LogUtils.e("Problem request successful : ${item.pid}")
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
        val length = args[mQueryParametersLimit] as Int
        if (args[mQueryParametersPId] != null) {
            selectionArgs.add(args[mQueryParametersPId].toString())
            selection = addSelection(selection, ProblemTable.PID)
            selection = addCmdParameters((args[mQueryParametersCmd] as String), selection)
        }
        if (args[mQueryParametersTitle] != null) {
            selectionArgs.add(args[mQueryParametersTitle].toString())
            selection = addSelection(selection, ProblemTable.TITLE + " like %?%")
        }
        if (args[mQueryParametersSource] != null) {
            selectionArgs.add(args[mQueryParametersSource].toString())
            selection = addSelection(selection, ProblemTable.SOURCE + " like %?%")
        }
        if (args[mQueryParametersOrder] != null) {
            order = ProblemTable.PID + " " + args[mQueryParametersOrder].toString()
        }
        val cursor: Cursor = mDataBase.query(
            ProblemTable.TABLENAME,
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
                cursor.getInt(cursor.getColumnIndex(ProblemTable.TIMELIMIT)),
                cursor.getInt(cursor.getColumnIndex(ProblemTable.MEMORYLIMIT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.DESCIPTION)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.INPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.OUTPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SAMPLEINPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SAMPLEOUTPUT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.HINT)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.SOURCE)),
                cursor.getString(cursor.getColumnIndex(ProblemTable.ADDEDTIME)),
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
        contentValues.put(ProblemTable.TIMELIMIT, data.time_limit)
        contentValues.put(ProblemTable.MEMORYLIMIT, data.memory_limit)
        contentValues.put(ProblemTable.DESCIPTION, data.description)
        contentValues.put(ProblemTable.INPUT, data.input)
        contentValues.put(ProblemTable.OUTPUT, data.output)
        contentValues.put(ProblemTable.SAMPLEINPUT, data.sample_input)
        contentValues.put(ProblemTable.SAMPLEOUTPUT, data.sample_output)
        contentValues.put(ProblemTable.HINT, data.hint)
        contentValues.put(ProblemTable.SOURCE, data.source)
        contentValues.put(ProblemTable.ADDEDTIME, data.added_time)
        contentValues.put(ProblemTable.ACCEPTED, data.accepted)
        contentValues.put(ProblemTable.SUBMISSION, data.submission)
        if (mDataBase?.update(
                ProblemTable.TABLENAME,
                contentValues,
                ProblemTable.PID + "=?",
                arrayOf("${data.pid}")
            ) == 0
        ) {
            mDataBase.insert(ProblemTable.TABLENAME, null, contentValues)
        }
    }

}