package com.richodemus.advent_of_code.two_thousand_sixteen.day4_rooms

import com.richodemus.advent_of_code.two_thousand_sixteen.toFile
import org.assertj.core.api.Assertions.assertThat

fun main(args: Array<String>) {
    val validRooms = "day4/rooms.txt".toFile().readLines().map(::Room).filter(Room::valid)
    val sum = validRooms.map(Room::sectorId).sum()
    println("The sum of the sector IDs of all valid rooms is $sum")
    assertThat(sum).isEqualTo(278221)

    val id = validRooms.filter { it.decrypt() == "northpole object storage" }
            .map(Room::sectorId)
            .first()

    println("The room with the North Pole Objects is: $id")
    assertThat(id).isEqualTo(257)
}

private class Room(raw: String) {
    val name: String
    val sectorId: Int
    val checksum: String

    init {
        val (name, sectorId, checksum) = raw.parse()
        this.name = name
        this.sectorId = sectorId
        this.checksum = checksum
    }

    override fun toString() = "$name-$sectorId[$checksum]"

    fun valid(): Boolean {
        val characterOccourances = name.toSortedListOfCharacterOccurrences()
        checksum.toList().forEachIndexed { i, c ->
            if (c != characterOccourances[i]) {
                return false
            }
        }
        return true
    }

    fun decrypt(): String {
        return name.toList()
                .map { it.rotate(sectorId) }
                .map { it.toString() }
                .reduce { string, char -> string + char }
    }
}

// qfkkj-nsznzwlep-dstaatyr-223[aknst]
private fun String.parse(): Triple<String, Int, String> {
    val regex = "([\\w-]*)-(\\d+)\\[(\\w+)\\]"
    val parser = Regex(regex)
    val parsed = parser.findAll(this).toList()[0]
    val b = parsed.groups[1]!!.value
    val c = parsed.groups[2]!!.value
    val d = parsed.groups[3]!!.value
    return Triple(b, c.toInt(), d)
}

private fun String.toSortedListOfCharacterOccurrences() = this.toList()
        .filterNot { it == '-' }
        .sorted()
        .groupBy { it }
        .map { Pair(it.key, it.value) }
        .map { Pair(it.first, it.second.size) }
        .sortedByDescending { it.second }
        .map { it.first }

private fun Char.rotate(steps: Int): Char {
    if (this == '-') {
        return ' '
    }
    var rotated = this
    1.rangeTo(steps).forEach {
        rotated++
        if (rotated > 'z') {
            rotated = 'a'
        }
    }
    return rotated
}
