package com.example.bs_skill.data

import org.jsoup.Jsoup
import org.jsoup.select.Elements

val url = "https://dak.gg/bser/characters/Nadine"

fun getCharacter(characterUrl: String = url): Elements {
    val doc = Jsoup.connect(characterUrl).timeout(1000 * 10).get()
    val characterList : Elements = doc.select(".characters-list li")
    return characterList
}

//fun getCharacterImg(): Elements {
//    val doc = Jsoup.connect(url).timeout(1000 * 10).get()
//}

fun getSkillImg(): Elements {
    val doc = Jsoup.connect(url).timeout(1000 * 10).get()
    val skillList : Elements = doc.select(".skill-guide__tab li")
    return skillList
}

fun getSkillUpgrade(): Elements {
    val doc = Jsoup.connect(url).timeout(1000 * 10).get()
    val upgrade : Elements = doc.select(".character-skills-table")
    return upgrade
}
