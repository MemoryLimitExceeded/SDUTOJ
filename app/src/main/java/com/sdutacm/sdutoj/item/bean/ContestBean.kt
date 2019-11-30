package com.sdutacm.sdutoj.item.bean

data class ContestBean constructor(
    val cid: Int = 0,
    val name: String = "",
    val type: Int = 0,
    val start_time: String = "",
    val end_time: String = "",
    val register_start_time: String = "",
    val register_end_time: String = ""
)