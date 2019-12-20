package com.sdutacm.sdutoj.ui.fragment.item

import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.adapter.recyclerview.itemfragment.ContestItemAdapter
import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.bean.ProblemItemBean
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity.Companion.TITLE_TYPE
import com.sdutacm.sdutoj.item.entity.itemfragment.SingleContestEntity.Companion.PROBLEM_ITEM_TYPE
import com.sdutacm.sdutoj.mvp.base.BaseModel.Companion.EMPTY_DATA
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.mvp.main.model.ContestModel
import com.sdutacm.sdutoj.mvp.main.presenter.ContestPresenter
import com.sdutacm.sdutoj.mvp.main.presenter.ContestPresenter.ContestDataHelper.Companion.CONTEST_ITEM_TYPE
import com.sdutacm.sdutoj.ui.activity.MainActivity
import com.sdutacm.sdutoj.ui.fragment.ViewPagerFragment
import com.sdutacm.sdutoj.ui.fragment.common.ItemFragment
import com.sdutacm.sdutoj.ui.fragment.item.ProblemItemFragment.Companion.NEED_REQUEST_BEAN_TYPE
import java.lang.StringBuilder

class ContestItemFragment : ItemFragment<SingleContestEntity>() {

    private var mProblemList: ArrayList<ProblemBean>? = null

    companion object {

        @JvmStatic
        fun newInstance(@LayoutRes contentLayout: Int, contestBean: ContestBean): ContestItemFragment {
            val fragment = ContestItemFragment()
            newInstance(contentLayout, fragment, contestBean)
            return fragment
        }

    }

    override fun getData() {
        val args = ContestPresenter.ContestDataHelper()
        args.setCid((data as ContestBean).cid)
            .setRequestType(CONTEST_ITEM_TYPE)
        mPresenter?.getMoreData(args)
    }

    override fun setData() {
        val bean = data as ContestBean
        val newDataList = ArrayList<SingleContestEntity>()
        newDataList.add(SingleContestEntity(bean, null,TITLE_TYPE))
        for (index in 0 until bean.problem.size) {
            newDataList.add(SingleContestEntity(bean.problem[index], getProblemId(index),PROBLEM_ITEM_TYPE))
        }
        mAdapter.setNewData(newDataList)
        if (bean.problem.size == 0) {
            Toast.makeText(context, "题目获取失败，请重试", Toast.LENGTH_SHORT).show()
            if (getRefreshing()) {
                setRefreshing(false)
                return
            }
        }
        hideLoading()
    }

    override fun initAdapter() {
        mAdapter = ContestItemAdapter(ArrayList())
        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (position != 0) {
                val data =
                    (adapter.data[position] as SingleContestEntity).mContent as ProblemItemBean
                val itemFragment = ProblemItemFragment.newInstance(
                    R.layout.item_fragment,
                    ProblemBean(NEED_REQUEST_BEAN_TYPE, data.pid)
                )
                itemFragment.setProblemId(getProblemId(position - 1))
                val fragment =
                    ViewPagerFragment.newInstance(R.layout.fragment_view_pager, itemFragment)
                val transaction =
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                transaction.addToBackStack(null)
                fragment.show(transaction, null)
            }
        }
    }

    override fun initPresenter() {
        mPresenter = ContestPresenter()
    }

    override fun initData() {
        showLoading()
        getData()
    }

    override fun updateData(data: Any?) {
    }

    override fun getModelFromView(): IMainContract.IMainModel? {
        return ContestModel()
    }

    override fun loadMoreData(data: Any) {
        when (data) {
            EMPTY_DATA -> {
                showNoPermission()
                return
            }
            is ArrayList<*> -> this.data = data[0] as Parcelable
            else -> this.data = data as Parcelable
        }
        setData()
    }

    private fun getProblemId(position: Int): String {
        var temp = position
        return if (temp == 0) {
            "A"
        } else {
            val listChar = ArrayList<Char>()
            while (temp != 0) {
                listChar.add('A'.plus(temp % 26))
                temp = temp / 26
            }
            val stringBuilder = StringBuilder()
            for (index in listChar.size - 1 downTo 0) {
                stringBuilder.append(listChar[index])
            }
            stringBuilder.toString()
        }
    }

}