package com.octopus.sample.http.interceptor

import com.octopus.sample.repository.UserInfoRepository
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
        private val mUserInfoRepository: UserInfoRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken = mUserInfoRepository.accessToken

        if (accessToken.isNotEmpty()) {
            val url = request.url.toString()
            request = request.newBuilder()
                    .addHeader("Authorization", accessToken)
                    .url(url)
                    .build()
        }

        return chain.proceed(request)
    }

}