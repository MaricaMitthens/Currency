package com.example.currency

data class CurrencyRate(
    val id: Int,
    val numCode: Int,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Float
) {}

