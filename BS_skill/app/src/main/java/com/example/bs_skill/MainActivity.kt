package com.example.bs_skill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bs_skill.Data.*
import com.example.bs_skill.Skill.SkillAdapter
import com.example.bs_skill.Skill.SkillViewModel
import com.example.bs_skill.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.nodes.Element

class MainActivity : AppCompatActivity() {

    private val viewModel: SkillViewModel by lazy {
        SkillViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

//        val binding = ActivityMainBinding.inflate(layoutInflater)
//
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // parse character img src

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.skillList.adapter = SkillAdapter()
    }
}

