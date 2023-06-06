package com.maxshapira.triosoftapp.data.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxshapira.triosoftapp.data.local.dao.PointDao
import com.maxshapira.triosoftapp.data.local.db.AppDatabase


class PointViewModel(context: Context) : ViewModel() {


    private val pointDao: PointDao = AppDatabase.getInstance(context).pointDao()

    val getAllPoints: LiveData<List<Point>> = pointDao.getAllPoints()

    fun addNewPoint(point: Point) = pointDao.insertPoint(point)


    fun getPointsByUserId(userId: String): LiveData<List<Point>> =
        pointDao.getPointsByUserId(userId)
}

class PointViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PointViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PointViewModel(context) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}



