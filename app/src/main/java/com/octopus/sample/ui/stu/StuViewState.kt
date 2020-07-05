package com.octopus.sample.ui.stu

import com.octopus.sample.entity.Check
import com.octopus.sample.entity.UserInfo

data class StuViewState(
        val msg: String?,
        val checkList: List<UserInfo>?
) {

    companion object {

        fun initial(): StuViewState {
            return StuViewState(
                    msg = null,
                    checkList = null
            )
        }
    }

}