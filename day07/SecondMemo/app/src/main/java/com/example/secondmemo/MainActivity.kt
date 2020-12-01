package com.example.secondmemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity(), SecondFragment.OnDataListener {
    var cnt = 0

    private lateinit var mainFragment : MainFragment
    private lateinit var inputFragment : SecondFragment
    lateinit var singleAdapter : SingleAdapter
    val items = ArrayList<SingleList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainFragment = MainFragment(items, this)
        inputFragment = SecondFragment(this)
        singleAdapter = SingleAdapter(this, items)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, MainFragment(items, this)).commit()
    }

    fun changeMainForm() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, mainFragment).commit()
    }

    fun changeInputForm() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, inputFragment).commit()
    }

    override fun onDataSet(title: String, content: String) {
        singleAdapter.addItem(SingleList(title, content, R.drawable.ic_launcher_foreground))
        cnt++
        changeMainForm()
    }
}