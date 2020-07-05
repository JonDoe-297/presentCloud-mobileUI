package com.octopus.sample.ui.main.profile

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.octopus.sample.base.Results
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.architecture.core.ext.postNext
import kotlinx.coroutines.launch

class ProfileViewModel(
        private val repo: ProfileRepository
) : BaseViewModel() {

    private val _viewStateLiveData: MutableLiveData<ProfileViewState> = MutableLiveData(ProfileViewState.initial())
    val viewStateLiveData: LiveData<ProfileViewState> = _viewStateLiveData

    init {
        getInfo()
    }

    fun getInfo() {
        viewModelScope.launch {
            when (val result = repo.getInfo()) {
                is Results.Failure -> _viewStateLiveData.postNext {
                    it.copy(userInfo = null)
                }
                is Results.Success -> _viewStateLiveData.postNext {
                    it.copy(userInfo = result.data.data)
                }
            }
        }
    }

    companion object {

        fun instance(fragment: Fragment, repo: ProfileRepository): ProfileViewModel =
                ViewModelProvider(fragment, ProfileViewModelFactory(repo)).get(ProfileViewModel::class.java)
    }
}

class ProfileViewModelFactory(
        private val repo: ProfileRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}