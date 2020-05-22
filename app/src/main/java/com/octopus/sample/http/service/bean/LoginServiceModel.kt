package com.octopus.sample.http.service.bean

/**
 * @Author: zy
 * @Date: 2020/5/22 15:49
 * @Description:
 */
data class LoginServiceModel(
        val username: String,
        val password: String,
        val name: String,
        val sno: String,
        val role: String
)