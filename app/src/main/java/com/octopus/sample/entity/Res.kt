package com.octopus.sample.entity

import com.google.gson.annotations.SerializedName


/**
 * @Author： zy
 * @Date： 2020/7/5 9:53 AM
 * @Description：
 */
data class Res(
        @SerializedName("filepath")
        val filepath: String,
        @SerializedName("filepathid")
        val filepathid: Int,
        @SerializedName("userid")
        val userid: Int
)