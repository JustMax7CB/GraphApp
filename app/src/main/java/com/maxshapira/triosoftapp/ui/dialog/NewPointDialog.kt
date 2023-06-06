package com.maxshapira.triosoftapp.ui.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.google.android.material.textfield.TextInputEditText
import com.maxshapira.triosoftapp.databinding.NewPointDialogLayoutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPointDialog(private val userId: String?) : DialogFragment() {

    private lateinit var binding: NewPointDialogLayoutBinding
    private lateinit var newPointDialogViewModel: NewPointDialogViewModel


    private lateinit var dateTimeEditText: TextInputEditText
    private lateinit var temperatureEditText: TextInputEditText
    private lateinit var humidityEditText: TextInputEditText
    private lateinit var temperatureGauge: HalfGauge
    private lateinit var humidityGauge: HalfGauge
    private lateinit var saveButton: Button


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        binding = NewPointDialogLayoutBinding.inflate(inflater, null, false)
        builder.setView(binding.root)
        bindWidgets()
        newPointDialogViewModel = NewPointDialogViewModel(requireContext(), dateTimeEditText)
        setupListeners()
        newPointDialogViewModel.setupTemperatureGauge(temperatureGauge)
        newPointDialogViewModel.setupHumidityGauge(humidityGauge)
        return builder.create()
    }

    private fun bindWidgets() {
        dateTimeEditText = binding.editTextDateTime
        temperatureEditText = binding.editTextTemperature
        humidityEditText = binding.editTextHumidity
        temperatureGauge = binding.gaugeTemperature
        humidityGauge = binding.gaugeHumidity
        saveButton = binding.buttonSave
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListeners() {
        dateTimeEditText.setOnClickListener {
            newPointDialogViewModel.getDateTimeCalendar()
            newPointDialogViewModel.pickDateTime()
        }


        temperatureEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is being changed.
                val temperatureValue = s.toString().toDoubleOrNull()
                temperatureGauge.value = temperatureValue ?: 0.0
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has been changed.
            }
        })

        humidityEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is being changed.
                val humidityValue = s.toString().toDoubleOrNull()
                humidityGauge.value = humidityValue ?: 0.0
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                newPointDialogViewModel.addNewPoint(
                    userId,
                    dateTimeEditText.text.toString(),
                    temperatureEditText.text.toString().toDouble(),
                    humidityEditText.text.toString().toDouble()

                )
                withContext(Dispatchers.Main) { dismiss() }
            }
        }


    }


}