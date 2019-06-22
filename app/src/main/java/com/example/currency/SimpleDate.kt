package com.example.currency

class SimpleDate(private var day: Int, private var month: Int, private var year: Int) {
    fun getAsSeparatedString(spr: Char): String {
        return DateUtils.utils.formatDate(day, month, year, spr)
    }

    override fun toString(): String {
        return DateUtils.utils.formatDate(day, month, year, '.')
    }

    fun getDay() = day
    fun getMonth() = month
    fun getYear() = year

    fun set(day: Int,month: Int, year: Int) {
        this.day = day
        this.month = month
        this.year = year
    }
}
