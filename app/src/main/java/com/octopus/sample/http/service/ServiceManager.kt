package com.octopus.sample.http.service

data class ServiceManager(val userService: UserService,
                          val loginService: LoginService)
