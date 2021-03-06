package com.octopus.sample.ui.res

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val CHECK_MODULE_TAG = "CHECK_MODULE_TAG"

val checkKodeinModule = Kodein.Module(CHECK_MODULE_TAG) {

    bind<DetailViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, LoginViewModelFactory(instance())).get(DetailViewModel::class.java)
    }

    bind<RemoteDataSource>() with singleton {
        RemoteDataSource(instance(), instance())
    }

    bind<DetailRepository>() with singleton {
        DetailRepository(instance())
    }
}