package com.octopus.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.octopus.sample.entity.ReceivedEvent
import com.octopus.sample.entity.Repo

@Database(
        entities = [ReceivedEvent::class, Repo::class],
        version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userReceivedEventDao(): UserReceivedEventDao

    abstract fun userReposDao(): UserReposDao
}