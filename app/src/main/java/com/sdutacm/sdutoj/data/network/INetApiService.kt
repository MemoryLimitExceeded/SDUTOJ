package com.sdutacm.sdutoj.data.network

import com.sdutacm.sdutoj.item.bean.ProblemBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INetApiService {

    @GET("Problem")
    fun getProblem(@Query("pid") pid: Int): Call<List<ProblemBean>>

}