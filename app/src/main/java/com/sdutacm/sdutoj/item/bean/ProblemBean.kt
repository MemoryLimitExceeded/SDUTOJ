package com.sdutacm.sdutoj.item.bean

data class ProblemBean constructor(
    val pid: Int = 0,
    val title: String = "",
    val time_limit: Int = 0,
    val memory_limit: Int = 0,
    val description: String = "",
    val input: String = "",
    val output: String = "",
    val sample_input: String = "",
    val sample_output: String = "",
    val hint: String = "",
    val source: String = "",
    val added_time: String = "",
    val accepted: Int = 0,
    val submission: Int = 0
) : BeanInterface
