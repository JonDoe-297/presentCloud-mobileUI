package com.octopus.sample.ui.register

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<RegisterViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, RegisterViewModelFactory(instance())).get(RegisterViewModel::class.java)
    }

    bind<LoginRemoteDataSource>() with singleton {
        LoginRemoteDataSource(instance())
    }

    bind<RegisterRepository>() with singleton {
        RegisterRepository(instance())
    }
}