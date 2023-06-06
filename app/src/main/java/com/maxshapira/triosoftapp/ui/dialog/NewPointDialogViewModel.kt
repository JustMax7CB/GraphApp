package com.maxshapira.triosoftapp.ui.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.widget.DatePicker
import android.widget.TimePicker
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.google.android.material.textfield.TextInputEditText

import com.maxshapira.triosoftapp.data.model.Point
import com.maxshapira.triosoftapp.data.model.PointViewModel
import java.util.Calendar

class NewPointDialogViewModel(
    private val context: Context,
    private val dateTimeEditText: TextInputEditText
) : DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    private var savedDay: Int = 0
    private var savedMonth: Int = 0
    private var savedYear: Int = 0
    private var savedHour: Int = 0
    private var savedMinute: Int = 0

    private val pointViewModel: PointViewModel = PointViewModel(context)

    fun setupTemperatureGauge(temperatureGauge: HalfGauge) {
        println("Setting up Temperature Gauge!")
        val freezingRange = Range()
        freezingRange.apply {
            color = Color.parseColor("#0000FF")
            from = -10.0
            to = 0.0
        }

        val coolRange = Range()
        coolRange.apply {
            color = Color.parseColor("#ADD8E6")
            from = 0.0
            to = 15.0
        }

        val mildRange = Range()
        mildRange.apply {
            color = Color.parseColor("#008000")
            from = 15.0
            to = 25.0
        }

        val warmRange = Range()
        warmRange.apply {
            color = Color.parseColor("#FFA500")
            from = 25.0
            to = 35.0
        }

        val hotRange = Range()
        hotRange.apply {
            color = Color.parseColor("#FF0000")
            from = 35.0
            to = 50.0
        }
        temperatureGauge.apply {
            addRange(freezingRange)
            addRange(coolRange)
            addRange(mildRange)
            addRange(warmRange)
            addRange(hotRange)
            minValue = -10.0
            maxValue = 50.0
            value = 25.0
            enableAnimation(true)
        }
    }

    fun setupHumidityGauge(humidityGauge: HalfGauge) {
        val lowRange = Range()
        lowRange.apply {
            color = Color.parseColor("#FFFF99")
            from = 0.0
            to = 30.0
        }

        val moderateRange = Range()
        moderateRange.apply {
            color = Color.parseColor("#ADD8E6")
            from = 30.0
            to = 60.0
        }

        val highRange = Range()
        highRange.apply {
            color = Color.parseColor("#90EE90")
            from = 60.0
            to = 80.0
        }

        val veryHighRange = Range()
        veryHighRange.apply {
            color = Color.parseColor("#E6E6FA")
            from = 80.0
            to = 100.0
        }


        humidityGauge.apply {
            addRange(lowRange)
            addRange(moderateRange)
            addRange(highRange)
            addRange(veryHighRange)
            minValue = 0.0
            maxValue = 100.0
            value = 45.0
            enableAnimation(true)

        }
    }


    fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    fun pickDateTime() {
        DatePickerDialog(context, this, year, month, day).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(context, this, hour, minute, true).show()

    }


    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        val minuteString = if (savedMinute < 10) "0$savedMinute" else "$savedMinute"
        val dateString = "$savedDay/$savedMonth/$savedYear\n$savedHour:$minuteString"

        dateTimeEditText.setText(dateString)
    }

    fun addNewPoint(
        userId: String?,
        dateTime: String,
        temperature: Double,
        humidity: Double
    ) {
        val newPoint = Point(userId = userId, dateTime = dateTime, temperature = temperature, humidity = humidity)
        pointViewModel.addNewPoint(newPoint)
    }


}