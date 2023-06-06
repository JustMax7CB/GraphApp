package com.maxshapira.triosoftapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.maxshapira.triosoftapp.MainActivity
import com.maxshapira.triosoftapp.data.local.dao.PointDao
import com.maxshapira.triosoftapp.data.local.db.AppDatabase
import com.maxshapira.triosoftapp.data.model.Point
import com.maxshapira.triosoftapp.data.model.PointViewModel
import com.maxshapira.triosoftapp.data.model.PointViewModelFactory
import com.maxshapira.triosoftapp.databinding.ActivityHomeBinding
import com.maxshapira.triosoftapp.network.firebase.FirebaseAuthManager
import com.maxshapira.triosoftapp.ui.chart.MyLineChart
import com.maxshapira.triosoftapp.ui.dialog.NewPointDialog


class HomeActivity : AppCompatActivity() {

    private lateinit var addNewPointButton: Button
    private lateinit var logoutButton: Button
    private lateinit var graphView: LineChart
    private lateinit var lineChart: MyLineChart

    private val firebaseAuthManager = FirebaseAuthManager
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pointDao: PointDao
    private lateinit var pointViewModel: PointViewModel
    private lateinit var pointViewModelFactory: PointViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        pointViewModelFactory = PointViewModelFactory(this)
        pointViewModel = pointViewModelFactory.create(PointViewModel::class.java)
        pointDao = AppDatabase.getInstance(this).pointDao()

        setContentView(binding.root)

        bindWidget()
        setupClickListeners()
        observePoints()

    }

    private fun bindWidget() {
        addNewPointButton = binding.buttonAddNewPoint
        logoutButton = binding.buttonLogout
        graphView = binding.graphView
    }


    private fun addNewPoint() {
        val dialog = NewPointDialog(firebaseAuthManager.getUserId())
        dialog.show(supportFragmentManager, "NewPointDialog")
    }

    private fun setupClickListeners() {
        addNewPointButton.setOnClickListener { addNewPoint() }
        logoutButton.setOnClickListener { logout() }
    }

    private fun observePoints() {
        firebaseAuthManager.getUserId()?.let {
            pointViewModel.getPointsByUserId(it).observe(this) { points ->
                Log.d("[observe]", points.toString())
                updateGraph(points)
            }
        }
    }

    private fun logout() {
        FirebaseAuthManager.signOut()
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    private fun updateGraph(points: List<Point>) {
        lineChart = MyLineChart(graphView, this, points)
        lineChart.onCreate()
    }


}