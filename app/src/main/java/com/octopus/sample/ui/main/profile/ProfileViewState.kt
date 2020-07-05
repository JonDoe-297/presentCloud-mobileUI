package com.octopus.sample.ui.main.profile

import com.octopus.sample.entity.UserInfo
import com.octopus.sample.manager.UserManager

data class ProfileViewState(
        val userInfo: UserInfo?
) {

    companion object {

        fun initial(): ProfileViewState {
            return ProfileViewState(
                    userInfo = UserManager.INSTANCE
            )
        }
    }
}