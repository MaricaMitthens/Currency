package com.example.currency

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryStorage {


    private val _hKEY: String = "history"

    private lateinit var sharedPreferences: SharedPreferences
    private var history: MutableList<HistoryOperation> = ArrayList()


    companion object {
        val instance = HistoryStorage()
    }

    fun init(prefs: SharedPreferences) {
        sharedPreferences = prefs
    }

    fun getHistory(): MutableList<HistoryOperation> {
        val rawData: String? = sharedPreferences.getString(_hKEY, "")
        if (rawData == "") {
            history = ArrayList()
            return history
        }

        val gson = Gson()

        val itemType = object : TypeToken<ArrayList<HistoryOperation>>() {}.type
        history = gson.fromJson(rawData, itemType)

        return history
    }

    fun addOperationToHistory(historyOperation: HistoryOperation) {
        history.add(0, historyOperation)

        if (history.size > 10) {
            history = history.dropLast(1).toMutableList()
        }
        val gson = Gson()
        val itemListJsonString = gson.toJson(history)

        val editor = sharedPreferences.edit()
        editor.putString(_hKEY, itemListJsonString)
        editor.apply()
    }
}