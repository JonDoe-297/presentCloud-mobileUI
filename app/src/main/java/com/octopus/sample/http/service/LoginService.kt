package com.octopus.sample.http.service

import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserAccessToken
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.bean.LoginRequestModel
import com.octopus.sample.http.service.bean.LoginServiceModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    suspend fun authorizations(@Body authRequestModel: LoginRequestModel): Response<UserAccessToken>

    @POST("register")
    suspend fun register(@Body loginServiceModel: LoginServiceModel): Response<CommonResp<Any>>

    @POST("login")
    suspend fun login(@Body loginServiceModel: LoginServiceModel): Response<CommonResp<UserInfo>>
}