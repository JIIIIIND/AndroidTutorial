package com.example.secondmemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class SingleAdapter(private val context : Context?, private val items : ArrayList<SingleList>) : BaseAdapter() {
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
        var singleItemView : SingleItemView? = if (convertView == null) {
            SingleItemView(context)
        } else {
            convertView as SingleItemView?
        }
        val item : SingleList = items[pos]
        singleItemView?.setName(item.name)
        singleItemView?.setMobile(item.mobile)
        singleItemView?.setImage(item.resId)
        return singleItemView
    }
}