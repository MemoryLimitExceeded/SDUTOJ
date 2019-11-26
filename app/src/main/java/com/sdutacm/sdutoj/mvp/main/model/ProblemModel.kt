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

    private var mPid = 0

    companion object {

        private val problemCache = SparseArray<ProblemBean>()

        const val problemRequestStartPid = 1000

    }

    override fun requestData(args: Any?): Any? {
        mPid = args as Int
        if (problemCache.get(mPid) == null) {
            requestDataFromDB()
        }
        if (problemCache.get(mPid) == null) {
            requestDataFromNetWork()
        }
        return problemCache.get(mPid)
    }

    override fun requestDataFromNetWork() {
        mService.getProblem(mPid).enqueue(object : Callback<List<ProblemBean>> {
            override fun onFailure(call: Call<List<ProblemBean>>, t: Throwable) {
                com.sdutacm.sdutoj.utils.LogUtils.e("Problem request fail : $t")
                mPresenter?.requestDataError()
            }

            override fun onResponse(
                call: Call<List<ProblemBean>>,
                response: Response<List<ProblemBean>>
            ) {
                val problemBeans = response.body()
                if (problemBeans?.size == 0) {
                    mPresenter?.requestSuccess(null)
                } else {
                    val problemBean: ProblemBean = problemBeans!![0]
                    problemCache.put(problemBean.pid, problemBean)
                    updateDatabase(problemBean)
                    com.sdutacm.sdutoj.utils.LogUtils.e("Problem request successful : ${problemBean.pid}")
                    mPresenter?.requestSuccess(problemBean)
                }
            }
        })
    }

    override fun requestDataFromDB() {
        val cursor: Cursor = mDataBase?.query(
            ProblemTable.TABLENAME,
            null,
            ProblemTable.PID + "=?",
            arrayOf("$mPid"),
            null,
            null,
            null
        ) ?: return
        while (cursor.moveToNext()) {
            problemCache.put(
                cursor.getInt(cursor.getColumnIndex(ProblemTable.PID)),
                ProblemBean(
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
            )
        }
        cursor.close()
        if (problemCache.get(mPid) != null) {
            requestDataFromNetWork()
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
                ProblemTable.TABLENAME, contentValues, ProblemTable.PID + "=?",
                arrayOf("${data.pid}")
            ) == 0
        ) {
            mDataBase.insert(ProblemTable.TABLENAME, null, contentValues)
        }
    }

    override fun updateData(index: Any) {
        mPid = index as Int
        requestDataFromNetWork()
    }

}