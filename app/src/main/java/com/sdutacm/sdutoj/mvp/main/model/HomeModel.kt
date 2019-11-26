package com.sdutacm.sdutoj.mvp.main.model

import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.ui.fragment.ContestFragment
import com.sdutacm.sdutoj.ui.fragment.ProblemFragment
import com.sdutacm.sdutoj.ui.fragment.StandingFragment
import com.sdutacm.sdutoj.ui.fragment.StatusFragment

class HomeModel : FragmentModel() {

    override fun updateData(index: Any) {
    }

    override fun updateDatabase(data: Any) {
    }

    override fun requestDataFromDB() {
    }

    override fun requestDataFromNetWork() {
    }

    override fun requestData(args : Any?): Any {
        return HomeDataHelper(
            arrayListOf(
                ProblemFragment.newInstance(R.layout.fragment_list),
                ContestFragment.newInstance(R.layout.fragment_list),
                StatusFragment.newInstance(R.layout.fragment_list),
                StandingFragment.newInstance(R.layout.fragment_list)
            )
        )
    }

    class HomeDataHelper(val fragmentList: ArrayList<BaseFragment>)

}