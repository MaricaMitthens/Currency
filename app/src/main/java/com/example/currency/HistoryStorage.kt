package com.example.currency

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class HistoryStorage {

    private val KEY:String = "history"

    private lateinit var sharedPreferences: SharedPreferences
    private var history: ArrayList<HistoryOperation> = ArrayList()

    data class SerializableHistory(
        @SerializedName("history") val history:ArrayList<HistoryOperation>
    )

    companion object {
        val instance = HistoryStorage()
    }

    fun init(prefs: SharedPreferences) {
        sharedPreferences = prefs
    }

    fun getHistory():ArrayList<HistoryOperation> {
        val rawData: String = sharedPreferences.getString(KEY, "[]")
        val deserializedHistory = Gson().fromJson<SerializableHistory>(rawData, SerializableHistory::class.java)
        history = deserializedHistory.history
        return history
    }

    fun addOperationToHistory(historyOperation: HistoryOperation) {
        history.add(historyOperation)
        val serializedHistory = Gson().toJson(SerializableHistory(history))
        var editor = sharedPreferences.edit()
        editor.putString(KEY, serializedHistory)
    }


}