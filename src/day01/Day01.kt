package day01

import readInput

fun main() {
    test("day-01-test-input-01", 4)
}

private fun test(filename: String, expected: Int) {
    val input = readInput("day01/$filename")

    val actual: Int = findPositionOfElfWithMostCalories(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

const val E = ""

private fun findPositionOfElfWithMostCalories(input: List<String>): Int {
    val tracker = ElfCalorieTracker()

    for (calories in input) {
        if (calories == E) {
            tracker.moveToNextElf()
            continue
        }

        tracker.addCaloriesToSum(calories)
    }

    return tracker.positionWithHighestCalories
}

class ElfCalorieTracker(
    var currentSum: Int = 0,
    var position: Int = 1,
    var highestCalories: Int = 0,
    var positionWithHighestCalories: Int = 1
) {


    fun moveToNextElf() {
        if (currentSum > highestCalories) updateHighestCalories()

        position++
        currentSum = 0
    }

    private fun updateHighestCalories() {
        highestCalories = currentSum
        positionWithHighestCalories = position
    }

    fun addCaloriesToSum(calories: String) {
        currentSum += calories.toInt()
    }
}
