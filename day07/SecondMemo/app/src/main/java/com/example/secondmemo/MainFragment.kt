package com.example.secondmemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment(val items : ArrayList<SingleList>, private val mainActivity : MainActivity) : Fragment() {
    lateinit var test : View
    lateinit var singleAdapter: SingleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test = view
        singleAdapter = SingleAdapter(context, items)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            mainActivity.changeInputForm()
        }
        view.findViewById<ListView>(R.id.mainListView).adapter = singleAdapter
    }
}
