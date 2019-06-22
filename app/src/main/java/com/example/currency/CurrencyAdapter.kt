package com.example.currency

import android.content.Context

import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_curr.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.time.LocalDate
import java.util.*
import javax.xml.datatype.DatatypeConstants
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class CurrencyAdapter(private val context: Context, private val selectedDate:SimpleDate) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    val client by lazy { CurrencyApiClient.create() }
    var currencies: ArrayList<Currency> = ArrayList()


    init {
        refreshCurrencyRate(selectedDate.getAsSeparatedString('/'))
    }

    class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val name =
            "${currencies[position].nominal} ${currencies[position].charCode}"
        var value = "${currencies[position].value} \u20BD"
        holder.view.curr_name.text = name
        holder.view.curr_value.text = value

    }

    override fun getItemCount() = currencies.size

    fun refreshCurrencyRate(date:String) {
        client.getCurrencyRate(date).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                currencies.clear()
                currencies.addAll(result.currs)
                notifyDataSetChanged()
            }, { error ->
                Toast.makeText(context, "Refresh error: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("ERRORS", error.message)
            })
    }
}


