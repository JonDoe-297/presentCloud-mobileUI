package com.octopus.sample.ui.check

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class DetailViewModel(
        private val repo: DetailRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<CheckViewState> = MutableLiveData(CheckViewState.initial())

    val stateLiveData: LiveData<CheckViewState> = _stateLiveData

    private lateinit var mClassNum: String

    fun getCheckinInfoList(classNum: String) {
        this.mClassNum = classNum
        viewModelScope.launch {
            when (val result = repo.getCheckinInfoList(classNum)) {
                is Results.Failure -> _stateLiveData.postNext {
                    it.copy(msg = result.error.toString(), checkList = null)
                }
                is Results.Success -> _stateLiveData.postNext {
                    it.copy(msg = result.data.msg, checkList = result.data.data)
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