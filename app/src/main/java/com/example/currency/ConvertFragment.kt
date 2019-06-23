package com.example.currency

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_convert.*

class ConvertFragment : Fragment() {

    private val selectedDate: SimpleDate = DateUtils.utils.todayAsDate()
    private val controller = ConverterController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    override fun onStart() {
        super.onStart()
        controller.requestRateForDate(this.context!!, selectedDate.getAsSeparatedString('/'))

        value_to.keyListener = null
        val currList = arrayOf("RUB", "USD", "EUR", "JPY")

        val adapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, currList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(1)

        val adapter2 = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, currList)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        tvDate.text = selectedDate.getAsSeparatedString('.')

        changeDateButton.setOnClickListener {
            chooseDate()
        }
        button.setOnClickListener {
            convert()
        }
    }

    private fun convert() {
        val curr1 = spinner.selectedItem.toString()
        val curr2 = spinner2.selectedItem.toString()
        var val1 = "0.00"
        if (value_from.text.isEmpty()) {
            value_from.setText(val1)
            value_to.setText(val1)
            saveInHistory(curr1, val1, curr2, val1)
            return
        }

        val1 = value_from.text.toString()
        if (val1 == "0.00") {
            value_to.setText(val1)
            saveInHistory(curr1, val1, curr2, val1)
            return
        }

        val val2 = controller.convert(curr1,val1.toFloat(),curr2)
        value_to.setText(val2)
        saveInHistory(curr1, val1, curr2, val2)
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

    private fun saveInHistory(from: String, valueFrom: String, to: String, valueTo: String) {
        HistoryStorage.instance.addOperationToHistory(
            HistoryOperation(
                "Conversion",
                "$valueFrom $from -> $valueTo $to (for ${selectedDate.getAsSeparatedString('.')})",
                DateUtils.utils.todayAsString()
            )
        )
    }
}

