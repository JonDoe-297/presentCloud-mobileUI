package com.octopus.sample.entity

import com.google.gson.annotations.SerializedName


/**
 * @Author： zy
 * @Date： 2020/7/4 9:54 PM
 * @Description：
 */
data class Check(
        @SerializedName("checkininfoid")
        val checkininfoid: Int,
        @SerializedName("classnum")
        val classnum: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("endtime")
        val endtime: String,
        @SerializedName("isvalid")
        val isvalid: Int,
        @SerializedName("starttime")
        val starttime: String
)