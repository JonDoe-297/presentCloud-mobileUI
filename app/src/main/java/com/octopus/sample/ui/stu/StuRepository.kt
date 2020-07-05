package com.octopus.sample.ui.stu

import com.octopus.sample.base.Results
import com.octopus.sample.entity.Check
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.UserInfo
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import retrofit2.http.Query

class DetailRepository(
        remoteDataSource: RemoteDataSource
) : BaseRepositoryRemote<RemoteDataSource>(remoteDataSource) {

    suspend fun getCheckinInfoList(classNum: String): Results<CommonResp<List<Check>>> {
        return remoteDataSource.getCheckinInfoList(classNum)
    }

    suspend fun getCheckinResult(classNum: String, checkinInfoId: String): Results<CommonResp<List<UserInfo>>> {
        return remoteDataSource.getCheckinResult(classNum, checkinInfoId)
    }

}

class RemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun getCheckinInfoList(classNum: String): Results<CommonResp<List<Check>>> {
        return processResponse {
            serviceManager.userService.getCheckinInfoList(classNum)
        }
    }

    suspend fun getCheckinResult(classNum: String, checkinInfoId: String): Results<CommonResp<List<UserInfo>>> {
        return processResponse {
            serviceManager.userService.getCheckinResult(classNum, checkinInfoId)
        }
    }

}
