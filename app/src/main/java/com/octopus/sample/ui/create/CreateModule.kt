package com.octopus.sample.ui.create

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val CREATE_MODULE_TAG = "CREATE_MODULE_TAG"

val loginKodeinModule = Kodein.Module(CREATE_MODULE_TAG) {

    bind<CreateViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, LoginViewModelFactory(instance())).get(CreateViewModel::class.java)
    }

    bind<LoginRemoteDataSource>() with singleton {
        LoginRemoteDataSource(instance(),instance())
    }

    bind<LoginRepository>() with singleton {
        LoginRepository(instance())
    }
}