package com.octopus.sample.ui.res

import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.Res
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import okhttp3.ResponseBody

class DetailRepository(
        remoteDataSource: RemoteDataSource
) : BaseRepositoryRemote<RemoteDataSource>(remoteDataSource) {

    suspend fun getCheckinInfoList(classNum: String): Results<CommonResp<List<Res>>> {
        return remoteDataSource.getCheckinInfoList(classNum)
    }

    suspend fun downloadFile(FilepathId: String): Results<ResponseBody> {
        return remoteDataSource.downloadFile(FilepathId)
    }

}

class RemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun getCheckinInfoList(classNum: String): Results<CommonResp<List<Res>>> {
        return processResponse {
            serviceManager.userService.getFilesByUserId(userInfoRepository.accessId)
        }
    }

    suspend fun downloadFile(FilepathId: String): Results<ResponseBody> {
        return processResponse {
            serviceManager.userService.downloadFile("http://39.108.90.94:8080/file/downloadByFilepathId?FilepathId=$FilepathId")
        }
    }

}
