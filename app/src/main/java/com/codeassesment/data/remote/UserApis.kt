package com.codeassesment.data.remote

import com.codeassesment.data.model.BaseResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApis {
    @GET("api")
    suspend fun getUserList(@Query("results") results: String): Response<BaseResponseModel>


}