package com.example.currency

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.hist_item.view.*


class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var operations: MutableList<HistoryOperation> = HistoryStorage.instance.getHistory()


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

        holder.view.operation_name.text = operation.operationName
        holder.view.operation.text = operation.operationDetails
        holder.view.operation_date.text = operation.operationDate
    }

    override fun getItemCount() = operations.size


}