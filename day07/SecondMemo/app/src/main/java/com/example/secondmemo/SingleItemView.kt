package com.example.secondmemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class SingleItemView @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var textView: TextView
    var textView2: TextView
    var imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.single_list, this, true)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        imageView = findViewById(R.id.imageView)
    }

    public fun setName(name: String) {
        textView.setText(name)
    }
    public fun setMobile(mobile: String) {
        textView2.setText(mobile)
    }
    public fun setImage(resId: Int) {
        imageView.setImageResource(resId)
    }
}