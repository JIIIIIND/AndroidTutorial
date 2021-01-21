package com.example.bs_skill

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bs_skill.data.Skill
import com.example.bs_skill.skill.SkillAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl != null) {
        Log.d("image: ", imgUrl)
    } else Log.d("image: ", "null")
    imgUrl?.let {
        Glide.with(imgView.context)
                .load(imgUrl)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground))
                .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Skill>?) {
    val adapter = recyclerView.adapter as SkillAdapter
    adapter.submitList(data)
}