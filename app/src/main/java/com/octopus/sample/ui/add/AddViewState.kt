package com.octopus.sample.ui.add

data class AddViewState(
        val isLoading: Boolean,
        val msg: String?,
        val addSuccess: Boolean
) {

    companion object {

        fun initial(): AddViewState {
            return AddViewState(
                    isLoading = false,
                    msg = null,
                    addSuccess = false
            )
        }
    }

}