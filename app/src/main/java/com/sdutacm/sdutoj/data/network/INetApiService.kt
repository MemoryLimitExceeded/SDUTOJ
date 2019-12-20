package com.sdutacm.sdutoj.data.network

import com.sdutacm.sdutoj.item.bean.ContestBean
import com.sdutacm.sdutoj.item.bean.ProblemBean
import com.sdutacm.sdutoj.item.bean.StatusBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface INetApiService {

    @GET("Problem")
    fun getProblem(@QueryMap map: MutableMap<String, Any>): Call<List<ProblemBean>>

    @GET("Solution")
    fun getSolution(@QueryMap map: MutableMap<String, Any>): Call<List<StatusBean>>

    @GET("Contest")
    fun getContest(@QueryMap map: MutableMap<String, Any>): Call<List<ContestBean>>

    @GET("ContestDetail")
    fun getContestDetail(@QueryMap map: MutableMap<String, Any>): Call<List<ContestBean>>

}