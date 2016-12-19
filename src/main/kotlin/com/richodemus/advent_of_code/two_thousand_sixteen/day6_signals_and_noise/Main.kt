package com.richodemus.advent_of_code.two_thousand_sixteen.day6_signals_and_noise

import com.richodemus.advent_of_code.two_thousand_sixteen.toFile
import org.assertj.core.api.Assertions.assertThat

fun main(args: Array<String>) {
    val sampleSignal = """eedadn
drvtee
eandsr
raavrd
atevrs
tsrnev
sdttsa
rasrtv
nssdts
ntnada
svetve
tesnvt
vntsnd
vrdear
dvrsen
enarar"""
    val sampleMessage = sampleSignal.toMessage().calculateMessageForMostCommon()
    assertThat(sampleMessage).isEqualTo("easter")

    val result = "day6/signals.txt".toFile().readText().toMessage().calculateMessageForMostCommon()
    assertThat(result).isEqualTo("umcvzsmw")
    println("The message is $result")

    val secondSampleMessage = sampleSignal.toMessage().calculateMessageForLeastCommon()
    assertThat(secondSampleMessage).isEqualTo("advent")

    val result2 = "day6/signals.txt".toFile().readText().toMessage().calculateMessageForLeastCommon()
    assertThat(result2).isEqualTo("rwqoacfz")
    println("The second message is $result2")
}

private fun String.toMessage(): Message {
    val signals: MutableList<String> = mutableListOf()

    val raw = this.split("\n")
    raw[0].forEachIndexed { i, ignore ->
        raw.map { it[i] }.joinToString("").let { signals.add(it) }

    }
    return Message(signals)
}


private class Message(val signals: List<String>) {
    fun calculateMessageForMostCommon() = calculateMessage { it.sortedByDescending { it.second } }
    fun calculateMessageForLeastCommon() = calculateMessage { it.sortedBy { it.second } }

    private fun calculateMessage(sorter: (x: List<Pair<Char, Int>>) -> List<Pair<Char, Int>>): String {
        return signals
                .map(String::toCharArray)
                .map {
                    it.sorted()
                            .groupBy { it }
                            .map { Pair(it.key, it.value) }
                            .map { Pair(it.first, it.second.size) }
                            .let { sorter.invoke(it) }
                            .map { it.first }
                }.map { it[0] }
                .joinToString("")
    }
}
