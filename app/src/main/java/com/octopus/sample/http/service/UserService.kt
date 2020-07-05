package com.octopus.sample.http.service

import com.octopus.sample.entity.*
import io.reactivex.Flowable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("class/getClassList")
    suspend fun getClassList(@Query("userId") userId: String): Response<CommonResp<List<ReceivedEvent>>>

    @GET("class/getJoinedClasses")
    suspend fun getJoinedClasses(@Query("userId") userId: String): Response<CommonResp<List<ReceivedEvent>>>

    @GET("getInfo")
    suspend fun getInfo(): Response<CommonResp<UserInfo>>

    @GET("class/addClass")
    suspend fun addClass(@Query("className") className: String, @Query("userId") userId: String): Response<CommonResp<ReceivedEvent>>

    @POST("class/addStudent")
    suspend fun addStudent(@Query("classNum") classNum: String, @Query("stuId") stuId: String): Response<CommonResp<Any>>

    @GET("class/getClassDetail")
    suspend fun getClassDetail(@Query("classNum") classNum: String): Response<CommonResp<ClassDetail>>

    @POST("checkin/addCheckinInfo")
    suspend fun addCheckinInfo(@Query("classNum") classNum: String, @Query("startTime") startTime: String,
                               @Query("endTime") endTime: String, @Query("code") code: String): Response<CommonResp<Any>>

    @GET("checkin/checkin")
    suspend fun checkin(@Query("classNum") classNum: String, @Query("userId") userId: String,
                        @Query("code") code: String): Response<CommonResp<Any>>

    @GET("checkin/getCheckinResult")
    suspend fun getCheckinResult(@Query("classNum") classNum: String, @Query("checkinInfoId") checkinInfoId: String): Response<CommonResp<List<UserInfo>>>

    @GET("checkin/getCheckinInfoList")
    suspend fun getCheckinInfoList(@Query("classNum") classNum: String): Response<CommonResp<List<Check>>>

    @GET("file/getFilesByUserId")
    suspend fun getFilesByUserId(@Query("userId") userId: String): Response<CommonResp<List<Res>>>

    @Multipart
    @POST("file/upload")
    suspend fun upload(@Query("userId") userId: String, @PartMap params: HashMap<String, RequestBody>): Response<CommonResp<Any>>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>

}