package com.octopus.sample.ui.register

import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.http.service.bean.LoginServiceModel
import com.octopus.sample.utils.processResponse

class RegisterRepository(
        remoteDataSource: LoginRemoteDataSource
) : BaseRepositoryRemote<LoginRemoteDataSource>(remoteDataSource) {

    suspend fun register(loginServiceModel: LoginServiceModel): Results<CommonResp<Any>> {
        return remoteDataSource.register(loginServiceModel)
    }
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    suspend fun register(loginServiceModel: LoginServiceModel): Results<CommonResp<Any>> {
        return processResponse {
            serviceManager.loginService.register(loginServiceModel)
        }
    }

}