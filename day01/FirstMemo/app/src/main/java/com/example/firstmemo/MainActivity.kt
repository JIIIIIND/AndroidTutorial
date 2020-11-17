package com.example.firstmemo

import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    var cnt : Int = 0
    var list = arrayListOf<SingleList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val listView : ListView = findViewById(R.id.mainListView)
        val listAdapter = SingleAdapter(this, list)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            listAdapter.addItem(SingleList("new Text", cnt.toString(), R.drawable.ic_launcher_foreground))
            cnt++
            listView.adapter = listAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class SingleAdapter(val context : Context, val items : ArrayList<SingleList>) : BaseAdapter() {
        override fun getCount() : Int {
            return items.size
        }
        fun addItem(item : SingleList) {
            items.add(item)
        }
        override fun getItem(pos : Int) : SingleList {
            return items[pos]
        }
        override fun getItemId(pos : Int) : Long {
            return pos.toLong();
        }
        override fun getView(pos : Int, convertView : View?, parent : ViewGroup) : SingleItemView? {
            var singleItemView : SingleItemView? = null
            singleItemView = if (convertView == null) {
                SingleItemView(applicationContext)
            } else {
                convertView as SingleItemView?
            }
            val item : SingleList = items.get(pos)
            singleItemView?.setName(item.name)
            singleItemView?.setMobile(item.mobile)
            singleItemView?.setImage(item.resId)
            return singleItemView
        }
    }
}