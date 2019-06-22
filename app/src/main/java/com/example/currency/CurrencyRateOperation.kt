package com.example.currency

import com.google.gson.annotations.SerializedName


data class CurrencyRateOperation(
    @SerializedName("operationDate") val operationDate: String,
    @SerializedName("rateDate") val rateDate: String
) : HistoryOperation(operationDate)