package com.example.currency

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.DocumentsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_curr.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlinx.android.synthetic.main.fragment_curr.*

import javax.xml.datatype.DatatypeConstants


class CurrFragment : Fragment() {
    lateinit var adapter: CurrencyAdapter

    val selectedDate: SimpleDate = DateUtils.utils.todayAsDate()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_curr, container, false)
    }

    override fun onStart() {
        super.onStart()

        tvDate.text = selectedDate.getAsSeparatedString('.')
        changeDateButton.setOnClickListener {
            Log.d("DATE1", "on click y = ${selectedDate.getYear()}, m = ${selectedDate.getMonth()},  d = ${selectedDate.getDay()}")
            chooseDate(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDay())
            Log.d("DATE1", "after chosen y = ${selectedDate.getYear()}, m = ${selectedDate.getMonth()},  d = ${selectedDate.getDay()}")

        }

        adapter = this.context?.let { CurrencyAdapter(it, selectedDate) }!!
        rv_item_list.layoutManager = LinearLayoutManager(this.context)
        rv_item_list.adapter = adapter
    }


    private fun chooseDate(current_year: Int, current_month: Int, current_day: Int) {
        val dpd =
            DatePickerDialog(this.context, DatePickerDialog.OnDateSetListener { _, year1, monthOfYear, dayOfMonth ->
                selectedDate.set(dayOfMonth, monthOfYear + 1, year1)
                val d = selectedDate.getAsSeparatedString('.')
                val d2 = selectedDate.getAsSeparatedString('/')
                tvDate.text = d
                adapter.refreshCurrencyRate(d2)
                HistoryStorage.instance.addOperationToHistory(CurrencyRateOperation("gte", d2))
            }, current_year, current_month - 1, current_day)

        dpd.datePicker.maxDate = System.currentTimeMillis() + 1000
        dpd.datePicker.minDate = 0
        dpd.show()
    }
}
