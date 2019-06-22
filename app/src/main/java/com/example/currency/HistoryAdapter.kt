package com.example.currency

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.hist_item.*
import kotlinx.android.synthetic.main.hist_item.view.*


class HistoryAdapter(private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var operations: ArrayList<HistoryOperation> = HistoryStorage.instance.getHistory()


    init {
        //
    }

    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hist_item, parent, false)

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val operation = operations[position]
        var name:String  = ""
        var details:String = ""
        var date:String = ""
        when (operation) {
            is ConvertationOperation -> {
                name = "Conversion"
                details =
                    "${operation.valueFrom} ${operation.currFrom}  ->  ${operation.valueTo} ${operation.currTo}  ${operation.rateDate}"
                date = operation.operationDate
            }
            is CurrencyRateOperation -> {
                name = "Currency Rate"
                details = operation.rateDate
                date = operation.operationDate
            }
        }


        holder.view.operation_name.text = name
        holder.view.operation.text = details
        holder.view.operation_date.text = date


    }

    override fun getItemCount() = operations.size


}