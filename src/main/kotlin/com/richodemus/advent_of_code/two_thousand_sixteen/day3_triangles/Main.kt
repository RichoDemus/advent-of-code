package com.richodemus.advent_of_code.two_thousand_sixteen.day3_triangles

import com.richodemus.advent_of_code.two_thousand_sixteen.toFile
import java.util.*

fun main(args: Array<String>) {
    val validTriangles = "day3/triangles.txt".toFile().readLines().map(::Triangle).filter { it.valid() }.count()
    println("There are $validTriangles valid triangles")
    assert(validTriangles == 869)

    val columnOne = mutableListOf<Int>()
    val columnTwo = mutableListOf<Int>()
    val columnThree = mutableListOf<Int>()
    "day3/triangles.txt".toFile().readLines().forEach {
        val split = it.trim().split(" ").filterNot(String::isBlank)
        columnOne.add(split[0].toInt())
        columnTwo.add(split[1].toInt())
        columnThree.add(split[2].toInt())
    }
    val queue: Queue<Int> = ArrayDeque((columnOne + columnTwo + columnThree))
    val triangles = mutableListOf<Triangle>()
    while (queue.isNotEmpty()) {
        triangles.add(Triangle(queue.poll(), queue.poll(), queue.poll()))
    }
    val validColumnTriangles = triangles.filter { it.valid() }.count()
    println("There are $validColumnTriangles valid triangles when looking at columns")
    assert(validColumnTriangles == 1544)
}

private class Triangle(unparsed: String) {
    val one: Int
    val two: Int
    val three: Int

    constructor(one:Int, two:Int, three:Int) : this("$one $two $three")

    init {
        val split = unparsed.trim().split(" ").filterNot(String::isBlank).sorted()
        one = split[0].toInt()
        two = split[1].toInt()
        three = split[2].toInt()
    }

    fun valid() = (one + two) > three && (one + three) > two && (two + three) > one
}
