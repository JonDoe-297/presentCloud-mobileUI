package com.octopus.sample.base

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.qingmei2.architecture.core.logger.initLogger
import com.octopus.sample.BuildConfig
import com.octopus.sample.di.*
import com.squareup.leakcanary.LeakCanary
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

open class BaseApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@BaseApplication }
        import(androidModule(this@BaseApplication))
        import(androidXModule(this@BaseApplication))

        import(serviceModule)
        import(dbModule)
        import(httpClientModule)
        import(serializableModule)
        import(globalRepositoryModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initLogger(BuildConfig.DEBUG)
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}