package com.example.currency

import android.content.Context
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_convert.*

class ConverterController {

    val client by lazy { CurrencyApiClient.create() }
    val currencies: ArrayList<Currency> = ArrayList()
    var currenciesMap: Map<String, Float> = mutableMapOf()

    fun convert(currFrom: String, valueFrom: Float, currTo: String): String {
        val rateFrom = currenciesMap[currFrom]
        if (rateFrom == null) {
            Log.d("CONVERTER_WARN", "$currFrom value is null")
            return "0"
        }

        val rateTo = currenciesMap[currTo]
        if (rateTo == null) {
            Log.d("CONVERTER_WARN", "$currTo value is null")
            return "0"
        }

        var res = Math.round(rateFrom * valueFrom / rateTo * 100.0) / 100.0
        return res.toString()
    }

    fun requestRateForDate(context: Context, date: String) {
        client.getCurrencyRate(date).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe({ result ->
            Log.d("CONVERTER_RES", result.toString())
            currencies.clear()
            currencies.addAll(result.currs)
            currencies.add(Currency("R0000", "Рубль", 1.0F, 1, "RUB", "0"))

            currenciesMap = currencies.map { it.charCode to it.value }.toMap()
        }, { error ->
            Toast.makeText(context, "Refresh error: ${error.message}", Toast.LENGTH_LONG).show()
            Log.e("ERRORS", error.message)
        })
    }
}