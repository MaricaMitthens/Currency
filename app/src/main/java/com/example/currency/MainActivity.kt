package com.example.currency

import android.app.TaskStackBuilder
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.LayoutDirection
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    private var currFragment: CurrFragment = CurrFragment()
    private var convertFragment: ConvertFragment = ConvertFragment()
    private var historyFragment: HistoryFragment = HistoryFragment()
    private var fragmentType: FragmentType = FragmentType.CURR


    //private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_curr -> {
                //textMessage.setText(R.string.title_curr)
                Log.d("HAHAH", "CURR   $fragmentType")
                if (fragmentType != FragmentType.CURR) {
                    showCurr()
                    fragmentType = FragmentType.CURR
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.navigation_convert -> {
                //textMessage.setText(R.string.title_convert)
                Log.d("HAHAH", "CONV    ${fragmentType}")
                if (fragmentType != FragmentType.CONVERT) {
                    showConvert()
                    fragmentType = FragmentType.CONVERT
                    return@OnNavigationItemSelectedListener true
                }

            }
            R.id.navigation_history -> {
                //textMessage.setText(R.string.title_history)
                Log.d("HAHAH", "HIST    ${fragmentType}")
                if (fragmentType != FragmentType.HISTORY) {
                    showHistory()
                    fragmentType = FragmentType.HISTORY
                    return@OnNavigationItemSelectedListener true
                }
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showCurr()
    }

    fun showCurr() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, currFragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showConvert() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, convertFragment)
        //transaction.addToBackStack(null)
        transaction.commit()

    }

    fun showHistory() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, historyFragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
}
