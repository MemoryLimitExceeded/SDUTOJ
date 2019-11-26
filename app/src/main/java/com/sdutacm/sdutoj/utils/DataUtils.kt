package com.sdutacm.sdutoj.utils

import com.sdutacm.sdutoj.item.bean.ProblemBean
import java.util.*

class DataUtils {
    companion object {
        val defaultProblemBean = ProblemBean(
            100, "cyk", 1000,
            1000, "", "", "", "",
            "", "", "", "1963-7-6 13:36:74", 10,
            20
        )

        fun getMainTabTitle()
                : Array<String> {
            return arrayOf<String>("Problems", "Contests", "Status", "Standings")
        }

        fun getActivityTabTitle()
                : Array<String> {
            return arrayOf("Home", "Discuss", "User")
        }

        fun getDisscussContent() {

        }

        fun getUserInfo() {

        }

        fun getProblemData() {}
    }
}