package com.maxshapira.triosoftapp.ui.chart

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.maxshapira.triosoftapp.R
import com.maxshapira.triosoftapp.data.model.Point
import kotlin.math.roundToInt

class MyLineChart(
    private val chart: LineChart,
    private val context: Context,
    private val pointsData: List<Point>
) {

    fun onCreate() {
        setUpLineChart()
        setLineChart()
    }

    private fun setUpLineChart() {


        with(chart) {

            animateX(1200, Easing.EaseInSine)
            val dateTimeAxis = xAxis
            val temperatureAxis = axisLeft
            val humidityAxis = axisRight
            extraBottomOffset = 30f
            extraLeftOffset = 30f
            extraRightOffset = 30f

            dateTimeAxis.apply {
                valueFormatter = DateTimeFormatter()
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1F
                setDrawGridLines(true)
                setDrawAxisLine(false)
                textSize = 15f
                labelRotationAngle = -30f
            }

            temperatureAxis.apply {
                isEnabled = true
                textColor = ContextCompat.getColor(context, R.color.black)
            }

            humidityAxis.apply {
                axisLineColor = Color.parseColor("#A7CDE8")
                textColor = ContextCompat.getColor(context, R.color.black)
            }

            with(legend) {
                isEnabled = true
                orientation = Legend.LegendOrientation.HORIZONTAL
                textSize = 12F
                formSize = 15F
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                form = Legend.LegendForm.LINE
            }
        }
    }

    inner class DateTimeFormatter() :
        IndexAxisValueFormatter() {

        private val dateTimes = ArrayList(pointsData.map { point -> point.dateTime })

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.roundToInt()
            return if (index == -1) {
                ""
            } else if (index < dateTimes.size) {
                dateTimes[index]
            } else {
                ""
            }
        }
    }

    private fun setLineChart() {

        val temperatureLine = LineDataSet(temperature(), "Temperature")
        temperatureLine.apply {
            lineWidth = 3f
            valueTextSize = 15f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = Color.parseColor("#4691C7")
            valueTextColor = ContextCompat.getColor(
                context,
                androidx.appcompat.R.color.abc_primary_text_material_light
            )
        }

        val humidityLine = LineDataSet(humidity(), "Humidity")
        humidityLine.apply {
            lineWidth = 3f
            valueTextSize = 15f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = Color.parseColor("#A7CDE8")
            valueTextColor = ContextCompat.getColor(context, R.color.black)
            enableDashedLine(20F, 10F, 0F)
        }

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.apply {
            add(temperatureLine)
            add(humidityLine)
        }
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()
    }

    private fun temperature(): ArrayList<Entry> {
        val temperatureData = ArrayList<Entry>()
        pointsData.forEachIndexed { index, point ->
            val temperature = point.temperature
            temperatureData.add(Entry(index.toFloat(), temperature.toFloat()))
        }
        return temperatureData
    }

    private fun humidity(): ArrayList<Entry> {
        val humidityData = ArrayList<Entry>()
        pointsData.forEachIndexed { index, point ->
            val humidity = point.humidity
            humidityData.add(Entry(index.toFloat(), humidity.toFloat()))
        }
        return humidityData
    }
}