package com.octopus.sample.ui.login

import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

@SuppressWarnings("checkResult")
class LoginViewModel(
        private val repo: LoginRepository
) : BaseViewModel() {

    private val _stateLiveData: MutableLiveData<LoginViewState> = MutableLiveData(LoginViewState.initial())

    val stateLiveData: LiveData<LoginViewState> = _stateLiveData

    init {
        viewModelScope.launch {
            val autoLoginEvent = repo.fetchAutoLogin()
            _stateLiveData.postNext { state ->
                state.copy(
                        isLoading = false,
                        msg = null,
                        autoLoginEvent = autoLoginEvent,
                        useAutoLoginEvent = true,
                        loginInfo = null
                )
            }
        }
    }

    fun onAutoLoginEventUsed() {
        _stateLiveData.postNext { state ->
            state.copy(isLoading = false, msg = null,
                    autoLoginEvent = null, useAutoLoginEvent = false, loginInfo = null)
        }
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            true -> _stateLiveData.postNext { state ->
                state.copy(isLoading = false, msg = "username or password can't be null",
                        loginInfo = null, autoLoginEvent = null)
            }
            false -> viewModelScope.launch {
                _stateLiveData.postNext {
                    it.copy(isLoading = true, msg = null, loginInfo = null, autoLoginEvent = null)
                }
                when (val result = repo.login(username, password)) {
                    is Results.Failure -> _stateLiveData.postNext {
                        it.copy(isLoading = false, msg = result.error.toString(), loginInfo = null, autoLoginEvent = null)
                    }
                    is Results.Success -> _stateLiveData.postNext {
                        repo.saveUserId(result.data.data.userid)
                        it.copy(isLoading = false, msg = result.data.msg, loginInfo = result.data.data, autoLoginEvent = null)
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
            LoginViewModel(repo) as T
}