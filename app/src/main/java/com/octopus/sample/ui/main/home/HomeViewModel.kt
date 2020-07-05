package com.octopus.sample.ui.main.home

import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.paging.PagingRequestHelper
import com.qingmei2.architecture.core.ext.paging.toLiveDataPagedList
import com.qingmei2.architecture.core.ext.postNext
import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.octopus.sample.entity.ReceivedEvent
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@SuppressWarnings("checkResult")
class HomeViewModel(
        private val repository: HomeRepository
) : BaseViewModel() {

    private val _viewStateLiveData: MutableLiveData<HomeViewState> = MutableLiveData(HomeViewState.initial())

    val pagedListLiveData: LiveData<PagedList<ReceivedEvent>>
        get() = repository
                .fetchEventDataSourceFactory()
                .toLiveDataPagedList(boundaryCallback = mBoundaryCallback)

    val viewStateLiveData: LiveData<HomeViewState> = _viewStateLiveData

    init {
        viewModelScope.launch {
            repository.clearData()
        }
    }

    private val mBoundaryCallback = HomeBoundaryCallback { result, pageIndex ->
        viewModelScope.launch {
            // Paging 预加载分页的结果，不需要对Error或者Refresh进行展示
            // 这会给用户一种无限加载列表的效果
            when (result) {
                is Results.Success -> if (!result.data.data.isNullOrEmpty()) {
                    when (pageIndex == 1) {
                        true -> repository.clearAndInsertNewData(result.data.data)
                        false -> repository.insertNewPageData(result.data.data)
                    }
                }
                else -> Unit    // do nothing
            }
        }
    }

    /**
     * Refresh event list action.
     */
    fun refreshDataSource() {
        viewModelScope.launch {
            // 1.显示刷新状态
            _viewStateLiveData.postNext { last ->
                last.copy(isLoading = true, throwable = null)
            }
            when (val result = repository.fetchEventByPage()) {
                // 2.1 成功后，对数据库进行清空，并插入第一页的数据，取消刷新
                is Results.Success -> {
                    if (!result.data.data.isNullOrEmpty()) {
                        repository.clearAndInsertNewData(result.data.data)
                    }
                    _viewStateLiveData.postNext { last ->
                        last.copy(isLoading = false, throwable = null)
                    }
                }
                // 2.2 失败后，取消刷新
                is Results.Failure -> {
                    _viewStateLiveData.postNext { last ->
                        last.copy(isLoading = false, throwable = result.error)
                    }
                }
            }
        }
    }

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(fragment, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)
    }

    inner class HomeBoundaryCallback(
            @WorkerThread private val handleResponse: (Results<CommonResp<List<ReceivedEvent>>>, Int) -> Unit
    ) : PagedList.BoundaryCallback<ReceivedEvent>() {

        private val mExecutor = Executors.newSingleThreadExecutor()
        private val mHelper = PagingRequestHelper(mExecutor)

        override fun onZeroItemsLoaded() {
            mHelper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL, object : PagingRequestHelper.Request {
                override fun run(callback: PagingRequestHelper.Request.Callback) {
                    viewModelScope.launch {
                        val result = repository.fetchEventByPage()
                        handleResponse(result, 1)
                        callback.recordSuccess()
                    }
                }
            })
        }

        override fun onItemAtEndLoaded(itemAtEnd: ReceivedEvent) {

        }
    }
}

class HomeViewModelFactory(private val repo: HomeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}