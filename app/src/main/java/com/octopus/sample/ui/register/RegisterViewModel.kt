package com.octopus.sample.ui.register

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.octopus.sample.http.service.bean.LoginServiceModel
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class RegisterViewModel(
        private val repo: RegisterRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<RegisterViewState> = MutableLiveData(RegisterViewState.initial())

    val stateLiveData: LiveData<RegisterViewState> = _stateLiveData

    fun reg(username: String?, password: String?, nick: String?, no: String?, role: String) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty() || nick.isNullOrEmpty() || no.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, msg = "信息不全")
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, msg = null)
                }
                when (val result = repo.register(LoginServiceModel(username, password, nick, no, role))) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = "network failure")
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.data.msg, success = result.data.code == 200)
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