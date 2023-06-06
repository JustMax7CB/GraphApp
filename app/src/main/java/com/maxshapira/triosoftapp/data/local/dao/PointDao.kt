package com.maxshapira.triosoftapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maxshapira.triosoftapp.data.model.Point

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoint(point: Point)

    @Query("SELECT * FROM point_table ORDER BY dateTime ASC")
    fun getAllPoints(): LiveData<List<Point>>

    @Query("SELECT * FROM point_table WHERE user_id = :userId ORDER BY dateTime ASC")
    fun getPointsByUserId(userId: String): LiveData<List<Point>>

}