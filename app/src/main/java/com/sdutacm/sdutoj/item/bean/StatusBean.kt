package com.sdutacm.sdutoj.item.bean

data class StatusBean constructor(
    val runid: Int = 0,
    val uid: Int = 0,
    val user_name: String = "",
    val pid: Int = 0,
    val cid: Int = 0,
    val result: Int = 0,
    val time: Int = 0,
    val memory: Int = 0,
    val language: String = "",
    val code_length: Int = 0,
    val submission_time: String = ""
)