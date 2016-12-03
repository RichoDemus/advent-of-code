package com.richodemus.advent_of_code.two_thousand_sixteen.day1_taxicab


import java.io.File
import java.net.URL

fun main(args: Array<String>) {
    val directions = readDirections()
    var position = Position()
    var direction = Direction.NORTH
    val previousPositions = mutableSetOf(position)
    var foundPrevLocation = false
    directions.forEach {
        direction = direction.turn(it.turn)
        IntRange(1, it.steps).forEach {
            position = position.move(direction, 1)
            if (previousPositions.contains(position) && !foundPrevLocation) {
                println("Previously visited point: ${position.toAnswer()}")
                foundPrevLocation = true
            }
            previousPositions.add(position)
        }
    }

    println("Answer: ${position.toAnswer()}")
}

fun readDirections() = ClassLoader.getSystemClassLoader().getResource("day1/directions.txt").toFile().readText().toDirections()

fun URL.toFile(): File = File(this.file)

fun String.toDirections() = this.split(",").map(String::trim).map { Action(it) }

data class Position(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Direction, steps: Int): Position {
        return when (direction) {
            Direction.NORTH -> Position(x, y + steps)
            Direction.SOUTH -> Position(x, y - steps)
            Direction.EAST -> Position(x + steps, y)
            Direction.WEST -> Position(x - steps, y)
        }
    }

    fun toAnswer() = Math.abs(x) + Math.abs(y)

    override fun toString(): String {
        return "Position(x=$x, y=$y)"
    }
}

data class Action(val turn: Turn, val steps: Int) {
    constructor(raw: String) : this(raw[0].toTurn(), raw.substring(1).toInt())
}

fun Char.toTurn() = if ('R' == this) Turn.RIGHT else Turn.LEFT

enum class Turn {
    RIGHT, LEFT
}

enum class Direction {
    NORTH {
        override fun turn(direction: Turn): Direction = if (direction == Turn.RIGHT) EAST else WEST
    },
    SOUTH {
        override fun turn(direction: Turn): Direction = if (direction == Turn.RIGHT) WEST else EAST
    },
    WEST {
        override fun turn(direction: Turn): Direction = if (direction == Turn.RIGHT) NORTH else SOUTH
    },
    EAST {
        override fun turn(direction: Turn): Direction = if (direction == Turn.RIGHT) SOUTH else NORTH
    };

    abstract fun turn(direction: Turn): Direction
}
