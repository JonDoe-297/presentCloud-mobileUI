package com.octopus.sample.http.service

import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.entity.Repo
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.bean.LoginServiceModel
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("class/getClassList")
    suspend fun getClassList(@Query("userId") userId: String): Response<CommonResp<List<ReceivedEvent>>>

    @GET("getInfo")
    suspend fun getInfo(): Response<CommonResp<UserInfo>>

}