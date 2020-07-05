package com.octopus.sample.ui.add

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

    suspend fun addStudent(classNum: String): Results<CommonResp<Any>> {
        return remoteDataSource.addStudent(classNum)
    }


}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun addStudent(classNum: String): Results<CommonResp<Any>> {
        return processResponse {
            serviceManager.userService.addStudent(classNum, userInfoRepository.accessId)
        }
    }
}
