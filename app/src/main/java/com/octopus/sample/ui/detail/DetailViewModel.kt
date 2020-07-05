package com.octopus.sample.ui.detail

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.octopus.sample.entity.CommonResp
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class DetailViewModel(
        private val repo: DetailRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<DetailViewState> = MutableLiveData(DetailViewState.initial())

    val stateLiveData: LiveData<DetailViewState> = _stateLiveData

    private lateinit var mClassNum: String

    fun getClassDetail(classNum: String) {
        mClassNum = classNum
        viewModelScope.launch {
            when (val result = repo.getClassDetail(classNum)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.error.toString(), classInfo = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.data.msg, classInfo = result.data.data)
                }
            }
        }
    }

    fun addCheckinInfo(startTime: String, endTime: String) {
        viewModelScope.launch {
            when (val result = repo.addCheckinInfo(mClassNum, startTime, endTime)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.error.toString(), classInfo = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.data.msg, classInfo = null)
                }
            }
        }
    }

    fun checkin() {
        viewModelScope.launch {
            when (val result = repo.checkin(mClassNum)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.error.toString(), classInfo = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.data.msg, classInfo = null)
                }
            }
        }
    }

    fun upload(path:String){
        viewModelScope.launch {
            when (val result = repo.upload(path)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.error.toString(), classInfo = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(isLoading = false, msg = result.data.msg, classInfo = null)
                }
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        private val repo: DetailRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            DetailViewModel(repo) as T
}