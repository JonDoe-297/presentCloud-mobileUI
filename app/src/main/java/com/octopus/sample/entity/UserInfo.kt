package com.octopus.sample.entity

import com.google.gson.annotations.SerializedName

data class UserInfo(
        @SerializedName("roleList")
        val roleList: List<Role>,
        @SerializedName("userDepartment")
        val userDepartment: String,
        @SerializedName("useraddress")
        val useraddress: String,
        @SerializedName("userbirthday")
        val userbirthday: String,
        @SerializedName("userid")
        val userid: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("usernation")
        val usernation: String,
        @SerializedName("usernickname")
        val usernickname: String,
        @SerializedName("userschool")
        val userschool: Any
)

data class Role(
        @SerializedName("accessName")
        val accessName: List<Any>,
        @SerializedName("role_createtime")
        val roleCreatetime: Any,
        @SerializedName("role_description")
        val roleDescription: String,
        @SerializedName("role_id")
        val roleId: Int,
        @SerializedName("role_name")
        val roleName: String
)
