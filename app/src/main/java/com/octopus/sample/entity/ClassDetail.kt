package com.octopus.sample.entity

import com.google.gson.annotations.SerializedName


/**
 * @Author： zy
 * @Date： 2020/7/4 3:32 PM
 * @Description：
 */
data class ClassDetail(
        @SerializedName("_class")
        val classX: Class,
        @SerializedName("teacher")
        val teacher: Teacher
)

data class Class(
        @SerializedName("classid")
        val classid: Int,
        @SerializedName("classmember")
        val classmember: Any,
        @SerializedName("classname")
        val classname: String,
        @SerializedName("classnum")
        val classnum: String,
        @SerializedName("studentList")
        val studentList: List<UserInfo>,
        @SerializedName("userid")
        val userid: Int
)

data class Teacher(
        @SerializedName("account")
        val account: Account,
        @SerializedName("rolesName")
        val rolesName: List<String>,
        @SerializedName("userDepartment")
        val userDepartment: Any,
        @SerializedName("useraddress")
        val useraddress: Any,
        @SerializedName("userbirthday")
        val userbirthday: Any,
        @SerializedName("userid")
        val userid: Int,
        @SerializedName("userisdelete")
        val userisdelete: Any,
        @SerializedName("username")
        val username: String,
        @SerializedName("usernation")
        val usernation: Any,
        @SerializedName("usernickname")
        val usernickname: Any,
        @SerializedName("usersno")
        val usersno: String
)

data class Account(
        @SerializedName("accountid")
        val accountid: Int,
        @SerializedName("loginaccount")
        val loginaccount: Any,
        @SerializedName("loginemail")
        val loginemail: Any,
        @SerializedName("loginpasswd")
        val loginpasswd: String,
        @SerializedName("loginphone")
        val loginphone: String
)