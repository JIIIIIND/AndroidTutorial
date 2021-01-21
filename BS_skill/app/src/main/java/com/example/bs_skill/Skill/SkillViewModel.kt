package com.example.bs_skill.Skill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bs_skill.Data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.nodes.Element

class SkillViewModel : ViewModel() {
    private val _character = MutableLiveData<List<Character>>()
    val character: LiveData<List<Character>>
        get() = _character

    private val _skill = MutableLiveData<List<Skill>>()
    val skill: LiveData<List<Skill>>
        get() = _skill

    private val _skillOrder = MutableLiveData<List<String>>()
    val skillOrder: LiveData<List<String>>
        get() = _skillOrder

    init {
        _character.value = getCharacterList()
        _skill.value = getSkillList()
        _skillOrder.value = getSkillOrderList()
    }

    fun getCharacterList() : List<Character>{
        val list = mutableListOf<Character>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getCharacter()
            for (data in result) {
                list.add(
                    Character(
                        data.child(0).childNode(1).attr("src"),
                        data.child(0).childNode(1).attr("alt")
                    )
                )
                _character.postValue(list)
            }
        }
        return list
    }

    fun getSkillList() : List<Skill>{
        val list = mutableListOf<Skill>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getSkillImg()
            for (data in result) {
                list.add(
                    Skill(
                        data.childNode(1).childNode(1).attr("src"),
                        data.childNode(1).childNode(1).attr("alt"),
                        "${data.childNode(1).childNode(3).childNode(0)}")
                )
                _skill.postValue(list)
            }
        }
        return list
    }

    fun getSkillOrderList() : List<String> {
        val list = mutableListOf<String>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getSkillUpgrade()
            val upgradeOrder = result[0].childNode(3).childNodes().filter { it is Element }
            for (data in upgradeOrder) {
                list.add("${data.childNode(1).childNode(0)}")
                _skillOrder.postValue(list)
            }
        }
        return list
    }
}