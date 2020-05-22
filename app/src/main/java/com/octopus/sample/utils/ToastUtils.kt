package com.octopus.sample.utils

import com.qingmei2.architecture.core.ext.toast
import com.octopus.sample.base.BaseApplication

inline fun toast(value: () -> String): Unit =
        BaseApplication.INSTANCE.toast(value)