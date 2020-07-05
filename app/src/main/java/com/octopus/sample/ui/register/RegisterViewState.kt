package com.octopus.sample.ui.register

data class RegisterViewState(
        val isLoading: Boolean,
        val msg: String?,
        val success: Boolean
) {

    companion object {

        fun initial(): RegisterViewState {
            return RegisterViewState(
                    isLoading = false,
                    msg = null,
                    success = false
            )
        }
    }

}