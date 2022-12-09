package day03

import readInput

fun main() {
    test1("day-03-test-input-01", 157)
}

private fun test1(filename: String, expected: Int) {
    val input = readInput("day03/$filename")

    val actual: Int = checkRucksacks(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

val priority = ('a' .. 'z') + ('A' .. 'Z')

fun checkRucksacks(sacks: List<String>): Int {
    return sacks
        .asSequence()
        .map(::Rucksack)
        .flatMap(Rucksack::findDuplicates)
        .map(priority::indexOf)
        .map(1::plus)
        .sum()
}

class Rucksack(contents: String) {
    private val capacity: Int = contents.length
    private val half = capacity / 2
    val firstCompartment: String = contents.substring(0 until half)
    val secondCompartment: String = contents.substring(half until capacity)

    fun findDuplicates(): Sequence<Char> =
        firstCompartment
            .asSequence()
            .filter(secondCompartment::contains)
            .distinct()
}