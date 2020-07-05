package com.octopus.sample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_received_events")
data class ReceivedEvent(
        @PrimaryKey
        val classid: Long,
        @ColumnInfo(name = "class_name")
        val classname: String,
        @ColumnInfo(name = "class_num")
        val classnum: String
) {
    var indexInResponse: Int = -1
}