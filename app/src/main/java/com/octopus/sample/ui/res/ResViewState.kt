package com.octopus.sample.ui.res

import com.octopus.sample.entity.Res

data class ResViewState(
        val msg: String?,
        val resList: List<Res>?
) {

    companion object {

        fun initial(): ResViewState {
            return ResViewState(
                    msg = null,
                    resList = null
            )
        }
    }

}