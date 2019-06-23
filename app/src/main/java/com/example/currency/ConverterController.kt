package com.example.currency

import android.content.Context
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt

class ConverterController {

    private val client by lazy { CurrencyApiClient.create() }
    private val currencies: ArrayList<Currency> = ArrayList()
    private var currenciesMap: Map<String, Float> = mutableMapOf()

    fun convert(currFrom: String, valueFrom: Float, currTo: String): String {
        val rateFrom = currenciesMap[currFrom]
        if (rateFrom == null) {
            return "0"
        }

        val rateTo = currenciesMap[currTo]
        if (rateTo == null) {
            return "0"
        }

        val res = (rateFrom * valueFrom / rateTo * 100.0).roundToInt() / 100.0
        return res.toString()
    }

    fun requestRateForDate(context: Context, date: String) {
        client.getCurrencyRate(date).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe({ result ->
            currencies.clear()
            currencies.addAll(result.currs)
            currencies.add(Currency("R0000", "Рубль", 1.0F, 1, "RUB", "0"))

            currenciesMap = currencies.map { it.charCode to it.value }.toMap()
        }, { error ->
            Toast.makeText(context, "Refresh error: ${error.message}", Toast.LENGTH_LONG).show()
        })
    }
}