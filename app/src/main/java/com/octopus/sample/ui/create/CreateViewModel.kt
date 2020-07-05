package com.octopus.sample.ui.create

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class CreateViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<CreateViewState> = MutableLiveData(CreateViewState.initial())

    val stateLiveData: LiveData<CreateViewState> = _stateLiveData

    fun addClass(className: String?) {
        when (className.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, msg = "className can't be null",
                        classInfo = null)
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, msg = null, classInfo = null)
                }
                when (val result = repo.addClass(className)) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.error.toString(), classInfo = null)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.data.msg, classInfo = result.data.data)
                    }
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
        private val repo: LoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            CreateViewModel(repo) as T
}