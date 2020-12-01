package com.example.secondmemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class SecondFragment(private val mainActivity : MainActivity) : Fragment() {
    private var onDataListener : OnDataListener? = null
    interface OnDataListener {
        fun onDataSet(title : String, content : String) : Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            onDataListener?.onDataSet(view.findViewById<EditText>(R.id.title).text.toString(),
                view.findViewById<EditText>(R.id.content).text.toString())
            view.findViewById<EditText>(R.id.title).text = null
            view.findViewById<EditText>(R.id.content).text = null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDataListener) {
            onDataListener = context
        }
    }
}