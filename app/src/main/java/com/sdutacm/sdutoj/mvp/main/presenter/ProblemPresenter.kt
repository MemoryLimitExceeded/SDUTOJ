package com.sdutacm.sdutoj.mvp.main.presenter

import com.sdutacm.sdutoj.mvp.main.common.FragmentModel
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel
import com.sdutacm.sdutoj.mvp.main.model.ProblemModel.Companion.mMinProblemPid

class ProblemPresenter : FragmentPresenter() {

    override fun getData(args: Any?) {
        if (args == null) {
            super.getData(
                ProblemModel.makeArgs(
                    mMinProblemPid,
                    null,
                    null,
                    FragmentModel.CommonQueryParameters.CMP_GREATER_OR_EQUAL.parameters,
                    FragmentModel.CommonQueryParameters.ORDER_ASC.parameters
                )
            )
        } else {
            super.getData(args)
        }
    }

    override fun getMoreData(args: Any) {
        if((args as Int)== mMinProblemPid){
            super.getMoreData(
                ProblemModel.makeArgs(
                    args,
                    null,
                    null,
                    FragmentModel.CommonQueryParameters.CMP_GREATER_OR_EQUAL.parameters,
                    FragmentModel.CommonQueryParameters.ORDER_ASC.parameters
                )
            )
        }else{
            super.getMoreData(
                ProblemModel.makeArgs(
                    args,
                    null,
                    null,
                    FragmentModel.CommonQueryParameters.CMP_GREATER.parameters,
                    FragmentModel.CommonQueryParameters.ORDER_ASC.parameters
                )
            )
        }
    }

}