package com.octopus.sample.utils

import com.qingmei2.architecture.core.logger.loge
import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.http.Errors
import retrofit2.Response
import java.io.IOException

fun <T> processApiResponse(response: Response<T>): Results<T> {
    return try {
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            Results.success(response.body()!!)
        } else {
            Results.failure(Errors.NetworkError(responseCode, responseMessage))
        }
    } catch (e: IOException) {
        Results.failure(Errors.NetworkError())
    }
}

inline fun <T> processResponse(request: () -> Response<T>): Results<T> {
    return try {
        val response = request()
        val responseCode = response.code()
        val responseMessage = response.message()
        if (response.isSuccessful) {
            Results.success(response.body()!!)
        } else {
            loge { "message:$responseMessage" }
            Results.failure(Errors.NetworkError(responseCode, responseMessage))
        }
    } catch (e: IOException) {
        loge { "catch:$e" }
        Results.failure(Errors.NetworkError())
    }
}