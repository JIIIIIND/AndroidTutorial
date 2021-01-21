package com.example.bs_skill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bs_skill.character.CharacterAdapter
import com.example.bs_skill.character.CharacterViewModel
import com.example.bs_skill.skill.SkillAdapter
import com.example.bs_skill.skill.SkillViewModel
import com.example.bs_skill.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: SkillViewModel by lazy {
        SkillViewModel()
    }
    private val characterGrid: CharacterViewModel by lazy {
        CharacterViewModel()
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
        binding.characterViewModel = characterGrid
        binding.characterList.adapter = CharacterAdapter()
    }
}

