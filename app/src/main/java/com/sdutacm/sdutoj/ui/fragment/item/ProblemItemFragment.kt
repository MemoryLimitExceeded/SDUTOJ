package com.sdutacm.sdutoj.ui.fragment.item

import android.os.Parcelable
import android.view.View
import androidx.annotation.LayoutRes
import com.sdutacm.sdutoj.adapter.recyclerview.itemfragment.ProblemItemAdapter
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.DESCRIPTION_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.HINT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_INPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SAMPLE_OUTPUT_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.SOURCE_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleProblemEntity.Companion.TITLE_TYPE
import com.sdutacm.sdutoj.mvp.base.BaseModel.Companion.EMPTY_DATA
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.mvp.main.presenter.ProblemPresenter
import com.sdutacm.sdutoj.ui.fragment.common.ItemFragment
import java.lang.StringBuilder

class ProblemItemFragment : ItemFragment<SingleProblemEntity>() {

    private var mProblemId: String? = null

    companion object {

        const val NEED_REQUEST_BEAN_TYPE = 1

        @JvmStatic
        fun newInstance(@LayoutRes contentLayout: Int, problemBean: ProblemBean): ProblemItemFragment {
            val fragment = ProblemItemFragment()
            newInstance(contentLayout, fragment, problemBean)
            return fragment
        }

    }

    override fun initView(rootView: View) {
        super.initView(rootView)
        if (!isNeedRequest()) {
            setData()
        } else {
            showLoading()
        }
    }

    override fun initData() {
        if (isNeedRequest()) {
            getData()
        }
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ProblemModel()
    }

    override fun initPresenter() {
        mPresenter = ProblemPresenter()
    }

    override fun updateData(data: Any?) {
    }

    override fun loadMoreData(data: Any) {
        if (data == EMPTY_DATA) {
            showNoPermission()
        } else {
            this.data = (data as ArrayList<*>)[0] as Parcelable
            setData()
        }
    }

    override fun getData() {
        val args = ProblemPresenter.ProblemDataHelper()
            .setPid((data as ProblemBean).pid)
        mPresenter?.getMoreData(args)
    }

    override fun setData() {
        val bean = data as ProblemBean
        val title = when {
            (mProblemId != null) -> mProblemId + ". " + bean.title
            else -> bean.title
        }
        val newDataList = ArrayList<SingleProblemEntity>()
        newDataList.add(
            SingleProblemEntity(
                arrayListOf(
                    title,
                    bean.time_limit.toString(),
                    bean.memory_limit.toString(),
                    bean.accepted.toString(),
                    bean.submission.toString()
                ), TITLE_TYPE
            )
        )
        newDataList.add(SingleProblemEntity(arrayListOf(bean.description), DESCRIPTION_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.input), INPUT_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.output), OUTPUT_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.sample_input), SAMPLE_INPUT_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.sample_output), SAMPLE_OUTPUT_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.hint), HINT_TYPE))
        newDataList.add(SingleProblemEntity(arrayListOf(bean.source), SOURCE_TYPE))
        mAdapter.setNewData(newDataList)
        hideLoading()
    }

    override fun initAdapter() {
        mAdapter = ProblemItemAdapter(ArrayList())
    }

    fun setProblemId(problemId: String) {
        this.mProblemId = problemId
    }

    private fun isNeedRequest(): Boolean {
        return (data as ProblemBean).type == NEED_REQUEST_BEAN_TYPE
    }

}