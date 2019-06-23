package com.example.currency

import com.google.gson.annotations.SerializedName

data class HistoryOperation(
    @SerializedName("operationName") val operationName: String,
    @SerializedName("operationDetails") val operationDetails: String,
    @SerializedName("operationDate") val operationDate: String
) {
}
