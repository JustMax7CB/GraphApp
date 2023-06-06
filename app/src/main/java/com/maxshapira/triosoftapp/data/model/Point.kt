package com.maxshapira.triosoftapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "point_table")
data class Point(
    @ColumnInfo(name = "user_id") val userId: String?,
    @PrimaryKey val dateTime: String,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "humidity") val humidity: Double
)
