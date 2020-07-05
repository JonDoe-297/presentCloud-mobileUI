package com.octopus.sample.ui.create

import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource

class LoginRepository(
        remoteDataSource: LoginRemoteDataSource
) : BaseRepositoryRemote<LoginRemoteDataSource>(remoteDataSource) {

    suspend fun addClass(className: String): Results<CommonResp<ReceivedEvent>> {
        return remoteDataSource.addClass(className)
    }


}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun addClass(className: String): Results<CommonResp<ReceivedEvent>> {
        return processResponse {
            serviceManager.userService.addClass(className, userInfoRepository.accessId)
        }
    }
}
