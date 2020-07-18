package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.models.User
import kotlin.concurrent.fixedRateTimer

object Utils {
    fun parseFullName(fullName:String?): Pair<String?, String?> {

        if (fullName?.length == 0 || fullName?.length == 1 && fullName.contains(" ")) {
            return Pair(null, null)
        }
        val parts : List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val map = fillTranslitMap()
        val builder = StringBuilder()

        for (char in payload.trim())
            builder.append(getTranslChar(char, map))

        return builder.toString().replace(" ", divider)
    }

    private fun getTranslChar(char: Char, map: HashMap<Char, String>): String {
        val transl  = map[char.toLowerCase()] ?: char.toString()

        return if (char.isUpperCase() && transl.isNotEmpty())
            transl.capitalize()
        else transl
    }

    private fun fillTranslitMap(): HashMap<Char, String> {
        val map = hashMapOf<Char, String>()
        map['а'] = "a"
        map['б'] = "b"
        map['в'] = "v"
        map['г'] = "g"
        map['д'] = "d"
        map['е'] = "e"
        map['ё'] = "e"
        map['ж'] = "zh"
        map['з'] = "z"
        map['и'] = "i"
        map['й'] = "i"
        map['к'] = "k"
        map['л'] = "l"
        map['м'] = "m"
        map['н'] = "n"
        map['о'] = "o"
        map['п'] = "p"
        map['р'] = "r"
        map['с'] = "s"
        map['т'] = "t"
        map['у'] = "u"
        map['ф'] = "f"
        map['х'] = "h"
        map['ц'] = "c"
        map['ч'] = "ch"
        map['ш'] = "sh"
        map['щ'] = "sh'"
        map['ъ'] = ""
        map['ы'] = "i"
        map['ь'] = ""
        map['э'] = "e"
        map['ю'] = "yu"
        map['я'] = "ya"

        return map
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) {
            return null
        } else if (firstName.isNullOrEmpty() || firstName.length == 1 && firstName.contains(" ")) {
            if (lastName?.trim()?.isEmpty()!!) {
                return null
            }
            return lastName?.get(0).toString().toUpperCase()
        } else if (lastName.isNullOrEmpty() || lastName.length == 1 && lastName.contains(" ")) {
            return firstName?.get(0).toString().toUpperCase()
        }
        return (firstName?.get(0).toString() + lastName?.get(0).toString()).toUpperCase()
    }
}