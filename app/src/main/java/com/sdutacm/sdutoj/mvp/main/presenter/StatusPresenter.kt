package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.mvp.main.model.StatusModel.Companion.makeArgs

class StatusPresenter : FragmentPresenter() {

    override fun getData(args: Any?) {
        if (args == null) {
            super.getData(makeArgs())
        } else {
            super.getData(args)
        }
    }

    override fun getMoreData(args: Any) {
        if ((args as Int) == -1) {
            super.getMoreData(makeArgs())
        } else {
            super.getMoreData(
                makeArgs(
                    args,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    FragmentModel.CommonQueryParameters.CMP_LESS.parameters
                )
            )
        }
    }

}