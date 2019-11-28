package com.sdutacm.sdutoj.data.network

import com.sdutacm.sdutoj.item.bean.ProblemBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface INetApiService {

    @GET("Problem")
    fun getProblem(@QueryMap map: MutableMap<String, Any>): Call<List<ProblemBean>>

}