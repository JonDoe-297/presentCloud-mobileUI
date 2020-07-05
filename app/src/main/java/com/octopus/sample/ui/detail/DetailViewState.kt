package com.octopus.sample.ui.detail

import com.octopus.sample.entity.ClassDetail

data class DetailViewState(
        val isLoading: Boolean,
        val msg: String?,
        val classInfo: ClassDetail?
) {

    companion object {

        fun initial(): DetailViewState {
            return DetailViewState(
                    isLoading = false,
                    msg = null,
                    classInfo = null
            )
        }
    }

}