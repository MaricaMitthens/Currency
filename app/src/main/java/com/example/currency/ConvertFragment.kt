package com.example.currency
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_convert.*
import kotlinx.android.synthetic.main.fragment_convert.changeDateButton
import kotlinx.android.synthetic.main.fragment_convert.tvDate
import kotlinx.android.synthetic.main.fragment_curr.*
import java.util.*
import javax.xml.datatype.DatatypeConstants
import kotlin.collections.ArrayList

class ConvertFragment: Fragment() {

    val client by lazy { CurrencyApiClient.create() }
    val currencies: ArrayList<Currency> = ArrayList()
    val selectedDate: SimpleDate = DateUtils.utils.todayAsDate()
    var curr_values: MutableMap<String, Float> = mutableMapOf("RUB" to 1.0f, "USD" to 6.0f, "EUR" to 8.1f, "JPY" to 9.3f)

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
        requestRateForDate(selectedDate.getAsSeparatedString('/'))
        arg2.keyListener = null
        val currList = arrayOf("RUB", "USD", "EUR", "JPY")
        val adapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, currList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val adapter2 = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, currList)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2
        val currentDateAsString = DateUtils.utils.todayAsString()
        tvDate.text = currentDateAsString
        val currentDate = DateUtils.utils.todayAsDate()
        changeDateButton.setOnClickListener {
            chooseDate(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDay())
        }
        button.setOnClickListener {
            convert()
        }
    }

    private fun convert(){
        //вызвать запрос??

        val curr1 = spinner.getItemAtPosition(spinner.selectedItemPosition)
        val curr2 = spinner2.selectedItem
        var val1 = "0.00"
        if (arg1.text.isEmpty()) {arg1.setText(val1)}
        val1 = arg1.text.toString()
        if (val1 == "0.00")  {
            arg2.setText(val1)
            return
        }
       // var va = Math.round(val1.toDouble() * 40 * 100.0) / 100.0
        val val2 = converter(curr1.toString(), curr2.toString(), val1.toFloat())
        val res = val2?.let { Math.round(it * 100.0) / 100.0 }
        arg2.setText("$res")

    }

    private fun converter(curr1:String, curr2:String, val3:Float): Float? {
        val val2 = (curr_values[curr1])?.times(val3)?.div(curr_values[curr2]!!)
        return val2

    }

    private fun chooseDate(current_year: Int, current_month: Int, current_day: Int) {
        val dpd =
            DatePickerDialog(this.context, DatePickerDialog.OnDateSetListener { _, year1, monthOfYear, dayOfMonth ->
                selectedDate.set(dayOfMonth, monthOfYear + 1, year1)
                val d = selectedDate.getAsSeparatedString('.')
                val d2 = selectedDate.getAsSeparatedString('/')
                tvDate.text = d
            }, current_year, current_month - 1, current_day)

        dpd.datePicker.maxDate = System.currentTimeMillis() + 1000
        dpd.datePicker.minDate = 0
        dpd.show()
    }

    private fun requestRateForDate(date: String) {
        client.getCurrencyRate(DateUtils.utils.todayAsString('/')).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()).subscribe({ result ->
            Log.d("CONVERTER_RES", result.toString())
            currencies.clear()
            currencies.addAll(result.currs)
        }, { error ->
            Toast.makeText(context, "Refresh error: ${error.message}", Toast.LENGTH_LONG).show()
            Log.e("ERRORS", error.message)
        })
    }

}

