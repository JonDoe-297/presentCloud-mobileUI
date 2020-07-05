package com.octopus.sample.ui.detail

import com.octopus.sample.base.Results
import com.octopus.sample.entity.ClassDetail
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File

class DetailRepository(
        remoteDataSource: RemoteDataSource
) : BaseRepositoryRemote<RemoteDataSource>(remoteDataSource) {

    suspend fun getClassDetail(classNum: String): Results<CommonResp<ClassDetail>> {
        return remoteDataSource.getClassDetail(classNum)
    }

    suspend fun addCheckinInfo(classNum: String, startTime: String, endTime: String): Results<CommonResp<Any>> {
        return remoteDataSource.addCheckinInfo(classNum, startTime, endTime)
    }

    suspend fun checkin(classNum: String): Results<CommonResp<Any>> {
        return remoteDataSource.checkin(classNum)
    }

    suspend fun upload(path: String): Results<CommonResp<Any>> {
        return remoteDataSource.upload(path)
    }

}

class RemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun getClassDetail(classNum: String): Results<CommonResp<ClassDetail>> {
        return processResponse {
            serviceManager.userService.getClassDetail(classNum)
        }
    }

    suspend fun addCheckinInfo(classNum: String, startTime: String, endTime: String): Results<CommonResp<Any>> {
        return processResponse {
            serviceManager.userService.addCheckinInfo(classNum, startTime, endTime, "")
        }
    }

    suspend fun checkin(classNum: String): Results<CommonResp<Any>> {
        return processResponse {
            serviceManager.userService.checkin(classNum, userInfoRepository.accessId, "")
        }
    }

    suspend fun upload(path: String): Results<CommonResp<Any>> {
        val file = File(path)
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val params = HashMap<String, RequestBody>()
        params["file\"; filename=\"" + file.name] = requestBody
        return processResponse {
            serviceManager.userService.upload(userInfoRepository.accessId, params)
        }
    }

}
