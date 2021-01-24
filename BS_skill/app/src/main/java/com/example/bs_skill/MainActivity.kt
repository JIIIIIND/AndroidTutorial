package com.example.bs_skill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bs_skill.character.CharacterAdapter
import com.example.bs_skill.character.CharacterViewModel
import com.example.bs_skill.skill.SkillAdapter
import com.example.bs_skill.skill.SkillViewModel
import com.example.bs_skill.databinding.ActivityMainBinding
import com.example.bs_skill.skill.SkillOrderAdapter

class MainActivity : AppCompatActivity() {

    private val characterGrid: CharacterViewModel by lazy {
        CharacterViewModel()
    }

    private val viewModel: SkillViewModel by lazy {
        SkillViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.characterViewModel = characterGrid
        binding.viewModel = viewModel
        binding.skillList.adapter = SkillAdapter()
        binding.skillOrderList.adapter = SkillOrderAdapter()
        binding.characterList.adapter = CharacterAdapter(CharacterAdapter.OnClickListener {
            characterGrid.changeUrl(it)
            characterGrid.changeCharacter(it)
            viewModel.getSkillList(characterGrid.url)
            viewModel.getSkillOrderList(characterGrid.url)
        })
    }
}
