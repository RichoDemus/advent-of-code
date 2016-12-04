package com.richodemus.advent_of_code.two_thousand_sixteen.day2_bathroom_keypad

import com.richodemus.advent_of_code.two_thousand_sixteen.toFile

fun main(args: Array<String>) {
    val lines = "day2/code.txt".toFile().readLines().toActions()
    var keypadPosition = KeypadPosition.FIVE
    var result = ""
    lines.forEach { actions ->
        actions.forEach { action ->
            keypadPosition = keypadPosition.move(action)
        }
        result += keypadPosition.value.toString()
    }
    println("The code is ${result}")
    assert("74921" == result)

    var weirdKeypadPosition = WeirdKeypadPosition.FIVE
    result = ""
    lines.forEach { actions ->
        actions.forEach { action ->
            weirdKeypadPosition = weirdKeypadPosition.move(action)
        }
        result += weirdKeypadPosition.value.toString()
    }
    println("The weird code is ${result}")
    assert("A6B35" == result)
}

private fun List<String>.toActions() = this.map { it.toCharArray().map(Char::toAction) }

/**
 * 123
 * 456
 * 789
 */
private enum class KeypadPosition(val value: Int) {
    ONE(1) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> ONE
                Action.DOWN -> FOUR
                Action.LEFT -> ONE
                Action.RIGHT -> TWO
            }
        }
    },
    TWO(2) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> TWO
                Action.DOWN -> FIVE
                Action.LEFT -> ONE
                Action.RIGHT -> THREE
            }
        }
    },
    THREE(3) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> THREE
                Action.DOWN -> SIX
                Action.LEFT -> TWO
                Action.RIGHT -> THREE
            }
        }
    },
    FOUR(4) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> ONE
                Action.DOWN -> SEVEN
                Action.LEFT -> FOUR
                Action.RIGHT -> FIVE
            }
        }
    },
    FIVE(5) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> TWO
                Action.DOWN -> EIGHT
                Action.LEFT -> FOUR
                Action.RIGHT -> SIX
            }
        }
    },
    SIX(6) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> THREE
                Action.DOWN -> NINE
                Action.LEFT -> FIVE
                Action.RIGHT -> SIX
            }
        }
    },
    SEVEN(7) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> FOUR
                Action.DOWN -> SEVEN
                Action.LEFT -> SEVEN
                Action.RIGHT -> EIGHT
            }
        }
    },
    EIGHT(8) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> FIVE
                Action.DOWN -> EIGHT
                Action.LEFT -> SEVEN
                Action.RIGHT -> NINE
            }
        }
    },
    NINE(9) {
        override fun move(action: Action): KeypadPosition {
            return when (action) {
                Action.UP -> SIX
                Action.DOWN -> NINE
                Action.LEFT -> EIGHT
                Action.RIGHT -> NINE
            }
        }
    };

    abstract fun move(action: Action): KeypadPosition
}

/**
1
2 3 4
5 6 7 8 9
A B C
D
 */
private enum class WeirdKeypadPosition(val value: Char) {
    ONE('1') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> ONE
                Action.DOWN -> THREE
                Action.LEFT -> ONE
                Action.RIGHT -> ONE
            }
        }
    },
    TWO('2') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> TWO
                Action.DOWN -> SIX
                Action.LEFT -> TWO
                Action.RIGHT -> THREE
            }
        }
    },
    THREE('3') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> ONE
                Action.DOWN -> SEVEN
                Action.LEFT -> TWO
                Action.RIGHT -> FOUR
            }
        }
    },
    FOUR('4') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> FOUR
                Action.DOWN -> EIGHT
                Action.LEFT -> THREE
                Action.RIGHT -> FOUR
            }
        }
    },
    FIVE('5') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> FIVE
                Action.DOWN -> FIVE
                Action.LEFT -> FIVE
                Action.RIGHT -> SIX
            }
        }
    },
    SIX('6') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> TWO
                Action.DOWN -> A
                Action.LEFT -> FIVE
                Action.RIGHT -> SEVEN
            }
        }
    },
    SEVEN('7') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> THREE
                Action.DOWN -> B
                Action.LEFT -> SIX
                Action.RIGHT -> EIGHT
            }
        }
    },
    EIGHT('8') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> FOUR
                Action.DOWN -> C
                Action.LEFT -> SEVEN
                Action.RIGHT -> NINE
            }
        }
    },
    NINE('9') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> NINE
                Action.DOWN -> NINE
                Action.LEFT -> EIGHT
                Action.RIGHT -> NINE
            }
        }
    },
    A('A') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> SIX
                Action.DOWN -> A
                Action.LEFT -> A
                Action.RIGHT -> B
            }
        }
    },
    B('B') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> SEVEN
                Action.DOWN -> D
                Action.LEFT -> A
                Action.RIGHT -> C
            }
        }
    },
    C('C') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> EIGHT
                Action.DOWN -> C
                Action.LEFT -> B
                Action.RIGHT -> C
            }
        }
    },
    D('D') {
        override fun move(action: Action): WeirdKeypadPosition {
            return when (action) {
                Action.UP -> B
                Action.DOWN -> D
                Action.LEFT -> D
                Action.RIGHT -> D
            }
        }
    };

    abstract fun move(action: Action): WeirdKeypadPosition
}

private enum class Action {
    UP, DOWN, LEFT, RIGHT
}

private fun Char.toAction(): Action {
    return when (this) {
        'U' -> Action.UP
        'D' -> Action.DOWN
        'L' -> Action.LEFT
        'R' -> Action.RIGHT
        else -> {
            throw RuntimeException("Unexpected Char: ${this}")
        }
    }
}