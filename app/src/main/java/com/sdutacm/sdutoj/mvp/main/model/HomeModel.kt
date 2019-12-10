package com.sdutacm.sdutoj.mvp.main.model

import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.ui.fragment.ContestFragment
import com.sdutacm.sdutoj.ui.fragment.ProblemFragment
import com.sdutacm.sdutoj.ui.fragment.StandingFragment
import com.sdutacm.sdutoj.ui.fragment.StatusFragment

class HomeModel : FragmentModel() {

    companion object {

        private const val PROBLEM_TAB_TITLE_NAME = "Problems"

        private const val CONTEST_TAB_TITLE_NAME = "Contests"

        private const val STATUS_TAB_TITLE_NAME = "Status"

        private const val STANDING_TAB_TITLE_NAME = "Standings"

    }

    override fun updateDatabase(data: Any) {
    }

    override fun requestData(args: Any?) {
        mPresenter?.requestSuccess(
            HomeDataHelper(
                arrayListOf(
                    ProblemFragment.newInstance(R.layout.fragment_list),
                    ContestFragment.newInstance(R.layout.fragment_list),
                    StatusFragment.newInstance(R.layout.fragment_list),
                    StandingFragment.newInstance(R.layout.fragment_list)
                ),
                arrayOf(
                    PROBLEM_TAB_TITLE_NAME,
                    CONTEST_TAB_TITLE_NAME,
                    STATUS_TAB_TITLE_NAME,
                    STANDING_TAB_TITLE_NAME
                )
            )
        )
    }

    override fun requestDataFromDB(args: HashMap<String, Any>) {
    }

    override fun requestDataFromNetWork(args: HashMap<String, Any>) {
    }

    class HomeDataHelper(
        val fragmentList: ArrayList<BaseFragment>,
        val fragmentTabTitle: Array<String>
    )

}