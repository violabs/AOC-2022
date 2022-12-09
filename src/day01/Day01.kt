package day01

import readInput

fun main() {
    test1("day-01-test-input-01", 24000)
    test2("day-01-test-input-01", 45000)
}

private fun test1(filename: String, expected: Int) {
    val input = readInput("day01/$filename")

    val actual: Int = findHighestCalorieSet(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

private fun test2(filename: String, expected: Int) {
    val input = readInput("day01/$filename")

    val actual: Int = findTop3HighestCalorieSets(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

const val E = ""

private fun findHighestCalorieSet(input: List<String>): Int {
    val tracker = ElfCalorieTracker()

    processInput(tracker, input)

    return tracker.sumOfCalorieAmounts(1)
}

private fun findTop3HighestCalorieSets(input: List<String>): Int {
    val tracker = ElfCalorieTracker()

    processInput(tracker, input)

    return tracker.sumOfCalorieAmounts(3)
}

private fun processInput(tracker: ElfCalorieTracker, input: List<String>) {
    for (calories in input) {
        if (calories == E) {
            tracker.moveToNextElf()
            continue
        }

        tracker.addCaloriesToSum(calories)
    }
}

class ElfCalorieTracker(
    var currentSum: Int = 0,
    var caloriesList: MutableList<Int> = mutableListOf()
) {

    fun moveToNextElf() {
        caloriesList.add(currentSum)

        currentSum = 0
    }

    fun addCaloriesToSum(calories: String) {
        currentSum += calories.toInt()
    }

    fun sumOfCalorieAmounts(takeAmount: Int): Int =
        caloriesList
            .also { it.add(currentSum) }
            .sortedDescending()
            .take(takeAmount)
            .sum()
}
