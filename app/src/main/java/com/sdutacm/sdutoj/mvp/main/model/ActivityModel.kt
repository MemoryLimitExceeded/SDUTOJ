package com.sdutacm.sdutoj.mvp.main.model

import com.sdutacm.sdutoj.R
import com.sdutacm.sdutoj.mvp.base.BaseFragment
import com.sdutacm.sdutoj.mvp.base.BaseModel
import com.sdutacm.sdutoj.mvp.base.BasePresenter
import com.sdutacm.sdutoj.mvp.main.IMainContract
import com.sdutacm.sdutoj.ui.activity.MainActivity
import com.sdutacm.sdutoj.ui.fragment.DiscussFragment
import com.sdutacm.sdutoj.ui.fragment.HomeFragment
import com.sdutacm.sdutoj.ui.fragment.UserFragment

class ActivityModel : BaseModel<BasePresenter<MainActivity, ActivityModel>>(),
    IMainContract.IMainModel {

    companion object {

        private const val HOME_TAB_TITLE_NAME = "Home"

        private const val DISCUSS_TAB_TITLE_NAME = "Discuss"

        private const val USER_TAB_TITLE_NAME = "User"

    }

    override fun requestMoreData(args: Any) {
    }

    override fun requestData(args: Any?) {
        val fragmentList: ArrayList<BaseFragment> = arrayListOf(
            HomeFragment.newInstance(R.layout.fragment_tablayout),
            DiscussFragment.newInstance(R.layout.fragment_list),
            UserFragment.newInstance(R.layout.fragment_list)
        )
        val titleList = arrayOf(HOME_TAB_TITLE_NAME, DISCUSS_TAB_TITLE_NAME, USER_TAB_TITLE_NAME)
        mPresenter?.requestSuccess(
            ActivityDataHelper(
                fragmentList,
                titleList
            )
        )
    }

    class ActivityDataHelper(
        val fragmentList: ArrayList<BaseFragment>,
        val titleList: Array<String>
    )

}