package com.octopus.sample.ui.login

import com.octopus.sample.base.Results
import com.octopus.sample.db.UserDatabase
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.http.service.bean.LoginServiceModel
import com.octopus.sample.manager.UserManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource

class LoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(remoteDataSource, localDataSource) {

    suspend fun login(username: String, password: String): Results<CommonResp<UserInfo>> {
        // 保存用户登录信息
        localDataSource.savePrefUser(username, password)
        val userInfo = remoteDataSource.login(LoginServiceModel(username, password, "", "", ""))

        // 如果登录失败，清除登录信息
        when (userInfo) {
            is Results.Failure -> localDataSource.clearPrefsUser()
            is Results.Success -> UserManager.INSTANCE = requireNotNull(userInfo.data.data)
        }
        return userInfo
    }

    fun fetchAutoLogin(): AutoLoginEvent {
        return localDataSource.fetchAutoLogin()
    }

    fun saveUserId(userId: String) {
        localDataSource.saveUserId(userId)
    }
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    suspend fun login(loginServiceModel: LoginServiceModel): Results<CommonResp<UserInfo>> {
        return processResponse {
            serviceManager.loginService.login(loginServiceModel)
        }
    }
}

class LoginLocalDataSource(
        private val db: UserDatabase,
        private val userRepository: UserInfoRepository
) : ILocalDataSource {

    fun savePrefUser(username: String, password: String) {
        userRepository.username = username
        userRepository.password = password
    }

    fun clearPrefsUser() {
        userRepository.username = ""
        userRepository.password = ""
    }

    fun fetchAutoLogin(): AutoLoginEvent {
        val username = userRepository.username
        val password = userRepository.password
        val isAutoLogin = userRepository.isAutoLogin
        return when (username.isNotEmpty() && password.isNotEmpty() && isAutoLogin) {
            true -> AutoLoginEvent(true, username, password)
            false -> AutoLoginEvent(false, "", "")
        }
    }

    fun saveUserId(userId: String) {
        userRepository.accessId = userId
    }
}

data class AutoLoginEvent(
        val autoLogin: Boolean,
        val username: String,
        val password: String
)