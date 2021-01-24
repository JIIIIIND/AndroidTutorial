package com.example.bs_skill

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bs_skill.character.CharacterAdapter
import com.example.bs_skill.data.Character
import com.example.bs_skill.data.Skill
import com.example.bs_skill.data.SkillOrder
import com.example.bs_skill.skill.SkillAdapter
import com.example.bs_skill.skill.SkillOrderAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl != null || imgUrl == "") {
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
                     data: ArrayList<Skill>?) {
    val adapter = recyclerView.adapter as SkillAdapter
    adapter.submitList(data)
}

@BindingAdapter("orderData")
fun bindOrderView(recyclerView: RecyclerView,
                  data: ArrayList<SkillOrder>?) {
    val adapter = recyclerView.adapter as SkillOrderAdapter
    adapter.submitList(data)
}

@BindingAdapter("characterList")
fun bindCharacterList(recyclerView: RecyclerView,
                     data: ArrayList<Character>?) {
    val adapter = recyclerView.adapter as CharacterAdapter
    adapter.submitList(data)
}