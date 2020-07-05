package com.octopus.sample.entity

import com.google.gson.annotations.SerializedName


/**
 * @Author: zy
 * @Date: 2020/6/30 16:37
 * @Description:
 */
data class CommonResp<T>(
        @SerializedName("code")
        val code: Int,
        @SerializedName("data")
        val `data`: T,
        @SerializedName("msg")
        val msg: String
)