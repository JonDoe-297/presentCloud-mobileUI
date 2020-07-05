package com.octopus.sample.ui.main.profile

import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource

class ProfileRepository(
        remoteDataSource: ProfileRemoteDataSource
) : BaseRepositoryRemote<ProfileRemoteDataSource>(remoteDataSource) {

    suspend fun getInfo(): Results<CommonResp<UserInfo>> {
        return remoteDataSource.getInfo()
    }

}

class ProfileRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    suspend fun getInfo(): Results<CommonResp<UserInfo>> {
        return processResponse {
            serviceManager.userService.getInfo()
        }
    }

}