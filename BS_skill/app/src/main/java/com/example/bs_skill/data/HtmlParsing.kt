package com.example.bs_skill.data

import org.jsoup.Jsoup
import org.jsoup.select.Elements

val baseUrl = "https://dak.gg/bser/characters/Adriana"

fun getCharacter(characterUrl: String = baseUrl): Elements {
    val doc = Jsoup.connect(characterUrl).timeout(1000 * 10).get()
    val characterList : Elements = doc.select(".characters-list li")
    return characterList
}

fun getSkillImg(characterUrl: String = baseUrl): Elements {
    val doc = Jsoup.connect(characterUrl).timeout(1000 * 10).get()
    val skillList : Elements = doc.select(".skill-guide__tab li")
    return skillList
}

fun getSkillUpgrade(characterUrl: String = baseUrl): Elements {
    val doc = Jsoup.connect(characterUrl).timeout(1000 * 10).get()
    val upgrade : Elements = doc.select(".character-skills-table__item")
    return upgrade
}
