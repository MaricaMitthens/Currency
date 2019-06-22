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
    var curr_values: MutableMap<String, Double> = mutableMapOf("RUB" to 1.0, "USD" to 6.0, "EUR" to 8.1, "JPY" to 9.3)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_curr, container, false)
    }

    override fun onStart() {
        super.onStart()


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val currentDate = "" + day + "." + MONTHS[month] + "." + year
        /*
        val values : Array<String> = arrayOf("GKELR  reoooo oeri", "GKELR  reoooo 2", "GKELR  reoooo3")
        val adapter = ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, values)
        histView.adapter = adapter*/

        tvDate.text = currentDate
        changeDateButton.setOnClickListener {
            chooseDate(year, month, day)
        }

        adapter = this.context?.let { CurrencyAdapter(it) }!!
        rv_item_list.layoutManager = LinearLayoutManager(this.context)
        rv_item_list.adapter = adapter
//        button.setOnClickListener {
//            var myDate = tvDate.text.toString().replace(".", "/")
//            Log.d("CURR_RES1", myDate)
//            adapter.refreshCurrencyRate(myDate)
//            Log.d("CURR_RES2", myDate)
//        }
    }


    private fun chooseDate(current_year: Int, current_month: Int, current_day: Int) {
        val dpd =
            DatePickerDialog(this.context, DatePickerDialog.OnDateSetListener { _, year1, monthOfYear, dayOfMonth ->
                val d = "" + dayOfMonth + "." + MONTHS[monthOfYear] + "." + year1
                val d2 = "" + dayOfMonth + "/" + MONTHS[monthOfYear] + "/" + year1
                tvDate.text = d
                adapter.refreshCurrencyRate(d2)
            }, current_year, current_month, current_day)
        dpd.datePicker.maxDate = System.currentTimeMillis() + 1000
        dpd.datePicker.minDate = 0
        dpd.show()

    }
}

private operator fun DatatypeConstants.Field.get(monthOfYear: Int): Any? {
    return monthOfYear
}