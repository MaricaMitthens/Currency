package com.example.currency

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currFragment: CurrFragment = CurrFragment()
    private var convertFragment: ConvertFragment = ConvertFragment()
    private var historyFragment: HistoryFragment = HistoryFragment()
    private var fragmentType: FragmentType = FragmentType.CURR


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_curr -> {
                if (fragmentType != FragmentType.CURR) {
                    showCurr()
                    fragmentType = FragmentType.CURR
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.navigation_convert -> {
                if (fragmentType != FragmentType.CONVERT) {
                    showConvert()
                    fragmentType = FragmentType.CONVERT
                    return@OnNavigationItemSelectedListener true
                }

            }
            R.id.navigation_history -> {
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

        val prefs = this.getSharedPreferences("appstorage", Context.MODE_PRIVATE)
        HistoryStorage.instance.init(prefs)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showCurr()
    }

    private fun showCurr() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, currFragment)
        transaction.commit()
    }

    private fun showConvert() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, convertFragment)
        transaction.commit()

    }

    private fun showHistory() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, historyFragment)
        transaction.commit()
    }
}
