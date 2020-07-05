package com.octopus.sample.ui.main.home

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.octopus.sample.base.Results
import com.octopus.sample.db.UserDatabase
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.http.service.ServiceManager
import com.octopus.sample.manager.UserManager
import com.octopus.sample.repository.UserInfoRepository
import com.octopus.sample.utils.processApiResponse
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource

@SuppressLint("CheckResult")
class HomeRepository(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    @MainThread
    suspend fun fetchEventByPage(): Results<CommonResp<List<ReceivedEvent>>> {
        return remoteDataSource.fetchEventsByPage()
    }

    @AnyThread
    suspend fun clearAndInsertNewData(items: List<ReceivedEvent>) {
        localDataSource.clearAndInsertNewData(items)
    }

    @AnyThread
    suspend fun clearData() {
        localDataSource.clearOldData()
    }

    @AnyThread
    suspend fun insertNewPageData(items: List<ReceivedEvent>) {
        localDataSource.insertNewPagedEventData(items)
    }

    @MainThread
    fun fetchEventDataSourceFactory(): DataSource.Factory<Int, ReceivedEvent> {
        return localDataSource.fetchPagedListFromLocal()
    }
}

class HomeRemoteDataSource(
        private val serviceManager: ServiceManager,
        private val userInfoRepository: UserInfoRepository
) : IRemoteDataSource {

    suspend fun fetchEventsByPage(): Results<CommonResp<List<ReceivedEvent>>> {
        val eventsResponse =
                if (UserManager.INSTANCE.roleList[0].roleName != "student") serviceManager.userService.getClassList(userInfoRepository.accessId)
                else serviceManager.userService.getJoinedClasses(userInfoRepository.accessId)
        return processApiResponse(eventsResponse)
    }
}

@SuppressLint("CheckResult")
class HomeLocalDataSource(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromLocal(): DataSource.Factory<Int, ReceivedEvent> {
        return db.userReceivedEventDao().queryEvents()
    }

    suspend fun clearOldData() {
        db.withTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
        }
    }

    suspend fun clearAndInsertNewData(data: List<ReceivedEvent>) {
        db.withTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
            insertDataInternal(data)
        }
    }

    suspend fun insertNewPagedEventData(newPage: List<ReceivedEvent>) {
        db.withTransaction { insertDataInternal(newPage) }
    }

    private suspend fun insertDataInternal(newPage: List<ReceivedEvent>) {
        val start = db.userReceivedEventDao().getNextIndexInReceivedEvents() ?: 0
        val items = newPage.mapIndexed { index, child ->
            child.indexInResponse = start + index
            child
        }
        db.userReceivedEventDao().insert(items)
    }
}