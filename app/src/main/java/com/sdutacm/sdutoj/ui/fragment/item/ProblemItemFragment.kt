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
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.mvp.main.presenter.ProblemPresenter
import com.sdutacm.sdutoj.ui.fragment.common.ItemFragment

class ProblemItemFragment : ItemFragment<SingleProblemEntity>() {

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayout: Int, problemBean: ProblemBean): ProblemItemFragment {
            val fragment = ProblemItemFragment()
            newInstance(contentLayout, fragment, problemBean)
            return fragment
        }

    }

    override fun initView(rootView: View) {
        super.initView(rootView)
        setData()
    }

    override fun initData() {
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
        this.data = (data as ArrayList<*>).get(0) as Parcelable
        setData()
    }

    override fun getData() {
        val args = mPresenter?.dataHelper as ProblemPresenter.ProblemDataHelper
        args.setPid((data as ProblemBean).pid)
        mPresenter?.getMoreData(args)
    }

    override fun setData() {
        val bean = data as ProblemBean
        mAdpater.setData(0, SingleProblemEntity(bean.title, TITLE_TYPE))
        mAdpater.setData(1, SingleProblemEntity(bean.description, DESCRIPTION_TYPE))
        mAdpater.setData(2, SingleProblemEntity(bean.input, INPUT_TYPE))
        mAdpater.setData(3, SingleProblemEntity(bean.output, OUTPUT_TYPE))
        mAdpater.setData(4, SingleProblemEntity(bean.sample_input, SAMPLE_INPUT_TYPE))
        mAdpater.setData(5, SingleProblemEntity(bean.sample_output, SAMPLE_OUTPUT_TYPE))
        mAdpater.setData(6, SingleProblemEntity(bean.hint, HINT_TYPE))
        mAdpater.setData(7, SingleProblemEntity(bean.source, SOURCE_TYPE))
        hideLoading()
    }

    override fun initAdapter() {
        showLoading()
        mAdpater = ProblemItemAdapter(ArrayList(8))
        for(i in 1..8){
            mAdpater.addData(SingleProblemEntity("",0))
        }
    }

}