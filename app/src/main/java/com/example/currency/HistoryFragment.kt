package com.example.currency
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_curr.*
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment: Fragment() {
    lateinit var adapter: HistoryAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onStart() {
        super.onStart()

        adapter = this.context?.let { context?.let { it1 -> HistoryAdapter(it1) } }!!
        histView.layoutManager = LinearLayoutManager(this.context)
        histView.adapter = adapter
    }
}