package com.example.currency

import java.util.*

class DateUtils {
    private lateinit var today: Calendar

    companion object {
        val utils = DateUtils()
    }

    fun todayAsString(spr: Char = '.'): String {
        if (!::today.isInitialized) {
            today = Calendar.getInstance()
        }

        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH) + 1
        val day: Int = today.get(Calendar.DAY_OF_MONTH)

        return formatDate(day, month, year, spr)
    }

    fun todayAsDate(): SimpleDate{
        if (!::today.isInitialized) {
            today = Calendar.getInstance()
        }

        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH) + 1
        val day: Int = today.get(Calendar.DAY_OF_MONTH)

        return SimpleDate(day, month, year)
    }

    fun formatDate(day: Int, month: Int, year: Int, spr: Char): String {
        val dayString: String = if (day < 10) "0$day" else day.toString()
        val monthString: String = if (month < 10) "0$month" else month.toString()

        return "$dayString$spr$monthString$spr$year"
    }
}