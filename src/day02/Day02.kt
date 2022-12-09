package day02


import readInput

fun main() {
    test("day-02-test-input-01", 15)
}

private fun test(filename: String, expected: Int) {
    val input = readInput("day02/$filename")

    val actual: Int = runGame(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

interface ScoreFactor {
    val score: Int
}

enum class Shape(
    override val score: Int
) : ScoreFactor {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        val symbolMap: Map<String, Shape> = mapOf(
            "A" to ROCK,
            "X" to ROCK,
            "B" to PAPER,
            "Y" to PAPER,
            "C" to SCISSORS,
            "Z" to SCISSORS
        )
    }
}

enum class Outcome(override val score: Int) : ScoreFactor {
    LOSE(0),
    DRAW(3),
    WIN(6);

    companion object {
        fun findOutcome(opponentShape: Shape, selfShape: Shape): Outcome {
            return when {
                (opponentShape == Shape.PAPER && selfShape == Shape.ROCK) ||
                (opponentShape == Shape.SCISSORS && selfShape == Shape.PAPER) ||
                (opponentShape == Shape.ROCK && selfShape == Shape.SCISSORS) -> LOSE

                (opponentShape == Shape.ROCK && selfShape == Shape.ROCK) ||
                (opponentShape == Shape.PAPER && selfShape == Shape.PAPER) ||
                (opponentShape == Shape.SCISSORS && selfShape == Shape.SCISSORS) -> DRAW

                (opponentShape == Shape.SCISSORS && selfShape == Shape.ROCK) ||
                (opponentShape == Shape.ROCK && selfShape == Shape.PAPER) ||
                (opponentShape == Shape.PAPER && selfShape == Shape.SCISSORS) -> WIN

                else -> throw Exception("Failed to compare shapes!")
            }
        }
    }
}

private fun runGame(input: List<String>): Int {
    return input
        .asSequence()
        .map { it.split(" ") }
        .map(::Round)
        .map(Round::score)
        .sum()
}

class Round(selections: List<String>) {
    private val opponentShape: Shape = Shape.symbolMap[selections[0]] ?: throw Exception("Missing opponent selection")

    private val myShape: Shape = Shape.symbolMap[selections[1]] ?: throw Exception("Missing my selection")

    private val outcome: Outcome = Outcome.findOutcome(opponentShape, myShape)

    val score: Int = myShape.score + outcome.score
}