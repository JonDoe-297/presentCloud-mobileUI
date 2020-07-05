package com.octopus.sample.ui.main.profile

import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource

class ProfileRepository(
        remoteDataSource: ProfileRemoteDataSource,
        localDataSource: LocalDataSource
) : BaseRepositoryBoth<ProfileRemoteDataSource, LocalDataSource>(remoteDataSource, localDataSource) {

    suspend fun getInfo(): Results<CommonResp<UserInfo>> {
        return remoteDataSource.getInfo()
    }

    fun clearPrefsUser() {
        localDataSource.clearPrefsUser()
    }

}

class ProfileRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    suspend fun getInfo(): Results<CommonResp<UserInfo>> {
        return processResponse {
            serviceManager.userService.getInfo()
        }
    }


}

class LocalDataSource(
        private val userRepository: UserInfoRepository
) : ILocalDataSource {

    fun clearPrefsUser() {
        userRepository.username = ""
        userRepository.password = ""
    }

}