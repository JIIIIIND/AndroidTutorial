package com.example.bs_skill.character

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bs_skill.data.Character
import com.example.bs_skill.data.getCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val _character = MutableLiveData<ArrayList<Character>>()
    val character: LiveData<ArrayList<Character>>
        get() = _character

    private val _characterImg = MutableLiveData<String>()
    val characterImg: LiveData<String>
        get() = _characterImg

    var url = "https://dak.gg/bser/characters/Nadine"

    init {
        getCharacterList()
    }

    private fun getCharacterList() : ArrayList<Character>{
        val list = ArrayList<Character>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = getCharacter()
            for (data in result) {
                list.add(
                        Character(
                                data.child(0).childNode(1).attr("src"),
                                data.child(0).childNode(1).attr("alt"),
                                data.child(0).attr("href")
                        )
                )
                _character.postValue(list)
                Log.d("character: ", list.get(list.size - 1).name)
            }
            _characterImg.postValue(character.value?.get(0)?.img)
        }
        return list
    }

    fun changeCharacter(character: Character?) {
        _characterImg.value = character?.img
    }

    fun changeUrl(character: Character) {
        url = character.url
    }
}