package com.octopus.sample.ui.add

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class CreateViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<AddViewState> = MutableLiveData(AddViewState.initial())

    val stateLiveData: LiveData<AddViewState> = _stateLiveData

    fun addClass(addStudent: String?) {
        when (addStudent.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, msg = "className can't be null",
                        addSuccess = false)
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, msg = null, addSuccess = false)
                }
                when (val result = repo.addStudent(addStudent)) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.error.toString(), addSuccess = false)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.data.msg, addSuccess = false)
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