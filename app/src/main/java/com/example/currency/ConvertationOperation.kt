package com.example.currency

import com.google.gson.annotations.SerializedName

data class ConvertationOperation(
    @SerializedName("operationDate") val operationDate: String,
    @SerializedName("currFrom") val currFrom: String,
    @SerializedName("currTo") val currTo: String,
    @SerializedName("valueFrom") val valueFrom: Float,
    @SerializedName("valueTo") val valueTo: Float,
    @SerializedName("rateDate") val rateDate: String
) : HistoryOperation(operationDate)

