package com.octopus.sample.ui.register

import com.octopus.sample.entity.UserInfo

data class RegisterViewState(
        val isLoading: Boolean,
        val throwable: Throwable?
) {

    companion object {

        fun initial(): RegisterViewState {
            return RegisterViewState(
                    isLoading = false,
                    throwable = null
            )
        }
    }

}