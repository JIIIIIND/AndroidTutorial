package com.example.bs_skill.skill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bs_skill.character.CharacterViewModel
import com.example.bs_skill.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.nodes.Element

class SkillViewModel : ViewModel() {

    private val _skill = MutableLiveData<ArrayList<Skill>>()
    val skill: LiveData<ArrayList<Skill>>
        get() = _skill

    private val _skillOrder = MutableLiveData<ArrayList<String>>()
    val skillOrder: LiveData<ArrayList<String>>
        get() = _skillOrder

    init {
        getSkillList()
        getSkillOrderList()
    }

    private fun getSkillList() : ArrayList<Skill>{
        val list = ArrayList<Skill>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getSkillImg()
            for (data in result) {
                list.add(
                    Skill(
                        data.childNode(1).childNode(1).attr("src"),
                        data.childNode(1).childNode(1).attr("alt"),
                        "${data.childNode(1).childNode(3).childNode(0)}")
                )
                Log.d("cskill: ", list.get(list.size - 1).name)
                _skill.postValue(list)
            }
        }
        return list
    }

    private fun getSkillOrderList() : List<String> {
        val list = ArrayList<String>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getSkillUpgrade()
            val upgradeOrder = result[0].childNode(3).childNodes().filter { it is Element }
            for (data in upgradeOrder) {
                list.add("${data.childNode(1).childNode(0)}")
                _skillOrder.postValue(list)
                Log.d("order: ", list.get(list.size - 1))
            }
        }
        return list
    }
}