package com.example.currency

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_convert.*
import kotlinx.android.synthetic.main.fragment_convert.changeDateButton
import kotlinx.android.synthetic.main.fragment_convert.tvDate

class ConvertFragment : Fragment() {

    val selectedDate: SimpleDate = DateUtils.utils.todayAsDate()
    val controller = ConverterController()
    var curr_values: MutableMap<String, Float> =
        mutableMapOf("RUB" to 1.0f, "USD" to 6.0f, "EUR" to 8.1f, "JPY" to 9.3f)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client by lazy { CurrencyApiClient.create() }

    }

    override fun onStart() {
        super.onStart()
        controller.requestRateForDate(this.context!!, selectedDate.getAsSeparatedString('/'))

        value_to.keyListener = null
        val currList = arrayOf("RUB", "USD", "EUR", "JPY")

        val adapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, currList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(1);

        val adapter2 = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, currList)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        val currentDateAsString = DateUtils.utils.todayAsString()
        tvDate.text = currentDateAsString

        val currentDate = DateUtils.utils.todayAsDate()
        changeDateButton.setOnClickListener {
            chooseDate()
        }
        button.setOnClickListener {
            convert()
        }
    }

    private fun convert() {
        //вызвать запрос??

        val curr1 = spinner.selectedItem.toString()
        val curr2 = spinner2.selectedItem.toString()
        var val1 = "0.00"
        if (value_from.text.isEmpty()) {
            value_from.setText(val1)
            value_to.setText(val1)
            return
        }
        val1 = value_from.text.toString()
        if (val1 == "0.00") {
            value_to.setText(val1)
            return
        }
        val val2 = controller.convert(curr1,val1.toFloat(),curr2)

        value_to.setText(val2)
    }

    private fun chooseDate() {
        val dpd =
            DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                selectedDate.set(day, month + 1, year)
                val d = selectedDate.getAsSeparatedString('.')
                val d2 = selectedDate.getAsSeparatedString('/')
                tvDate.text = d

                controller.requestRateForDate(this.context!!, d2)
            }, selectedDate.getYear(), selectedDate.getMonth() - 1, selectedDate.getDay())

        dpd.datePicker.maxDate = System.currentTimeMillis() + 1000
        dpd.datePicker.minDate = 0
        dpd.show()
    }

}

