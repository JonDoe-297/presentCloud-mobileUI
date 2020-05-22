package com.octopus.sample.ui.register

import androidx.lifecycle.*
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import com.octopus.sample.base.Results
import com.octopus.sample.http.Errors
import com.octopus.sample.http.service.bean.LoginServiceModel
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class RegisterViewModel(
        private val repo: RegisterRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<RegisterViewState> = MutableLiveData(RegisterViewState.initial())

    val stateLiveData: LiveData<RegisterViewState> = _stateLiveData

    fun login(username: String?, password: String?, nick: String?, no: String?, role: String) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty() || nick.isNullOrEmpty() || no.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, throwable = Errors.EmptyInputError)
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, throwable = null)
                }
                when (val result = repo.register(LoginServiceModel(username, password, nick, no, role))) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = result.error)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, throwable = null)
                    }
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(
        private val repo: RegisterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            RegisterViewModel(repo) as T
}