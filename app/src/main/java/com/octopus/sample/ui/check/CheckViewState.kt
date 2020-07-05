package com.octopus.sample.ui.check

import com.octopus.sample.entity.Check

data class CheckViewState(
        val msg: String?,
        val checkList: List<Check>?
) {

    companion object {

        fun initial(): CheckViewState {
            return CheckViewState(
                    msg = null,
                    checkList = null
            )
        }
    }

}