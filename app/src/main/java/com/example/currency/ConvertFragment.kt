package com.example.currency
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_convert.*
import kotlinx.android.synthetic.main.fragment_convert.changeDateButton
import kotlinx.android.synthetic.main.fragment_convert.tvDate
import kotlinx.android.synthetic.main.fragment_curr.*
import java.time.Year
import java.util.*
import javax.xml.datatype.DatatypeConstants

class ConvertFragment: Fragment() {
/*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }


    override fun onStart() {
        super.onStart()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val currentDate = " " + day + "." + DatatypeConstants.MONTHS[month] + "." + year
        tvDate.text = currentDate
        changeDateButton.setOnClickListener {
            chooseDate(year, month, day)
        }

    }



    private fun chooseDate(current_year : Int, current_month: Int, current_day: Int){
        val dpd = DatePickerDialog(this.context, DatePickerDialog.OnDateSetListener { _, year1, monthOfYear, dayOfMonth ->
            val d = " " + dayOfMonth + "." + DatatypeConstants.MONTHS[monthOfYear] + "." + year1
            tvDate.text = d
        }, current_year, current_month, current_day)
        dpd.datePicker.maxDate = System.currentTimeMillis() + 1000
        dpd.datePicker.minDate = 0
        dpd.show()
    }
}

private operator fun DatatypeConstants.Field.get(monthOfYear: Int): Any? {
    return  monthOfYear
}