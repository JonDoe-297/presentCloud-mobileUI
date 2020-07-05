package com.octopus.sample.ui.create

import com.octopus.sample.entity.ReceivedEvent

data class CreateViewState(
        val isLoading: Boolean,
        val msg: String?,
        val classInfo: ReceivedEvent?
) {

    companion object {

        fun initial(): CreateViewState {
            return CreateViewState(
                    isLoading = false,
                    msg = null,
                    classInfo = null
            )
        }
    }

}