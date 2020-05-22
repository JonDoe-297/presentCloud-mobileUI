package com.octopus.sample.di

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.room.Room
import com.octopus.sample.base.BaseApplication
import com.octopus.sample.db.UserDatabase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

private const val DB_MODULE_TAG = "DBModule"

val dbModule = Kodein.Module(DB_MODULE_TAG) {

    bind<UserDatabase>() with singleton {
        Room.databaseBuilder(BaseApplication.INSTANCE, UserDatabase::class.java, "user")
                .setTransactionExecutor(ArchTaskExecutor.getIOThreadExecutor())
                .fallbackToDestructiveMigration()
                .build()
    }
}