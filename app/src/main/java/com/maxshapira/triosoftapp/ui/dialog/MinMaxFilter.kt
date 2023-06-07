package com.maxshapira.triosoftapp.ui.dialog

import android.text.InputFilter
import android.text.Spanned

class MinMaxFilter(private val minValueString: String, private val maxValueString: String) :
    InputFilter {

    private val minValue = minValueString.toDouble()
    private val maxValue = maxValueString.toDouble()

    override fun filter(
        source: CharSequence?,
        staret: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            var newVal = dest.toString().substring(0, dstart) + dest.toString()
                .substring(dend, dest.toString().length)
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(
                dstart,
                newVal.length
            )
            if (newVal == "-" || newVal == ".") {
                if (end == 0)
                    return ""
                return newVal
            }

            val input = newVal.toDouble()
            if (isInRange(minValue, maxValue, input))
                return null
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ("")
    }


    private fun isInRange(a: Double, b: Double, c: Double): Boolean {
        return if (b > a) c in a..b else c in b..a
    }

}