import util.readFile
import kotlin.math.abs
import kotlin.math.pow

val input = readFile("day5.txt")

fun calculateSeatId(it: String): Int {
    var row = 0.0
    var col = 0.0
    for (i in 0..6) {
        if (it[i] == 'B') row += 2.0.pow(abs(i - 6))
    }
    for (i in 7..9) {
        if (it[i] == 'R') col += 2.0.pow(abs(i - 9))
    }
    return (row * 8 + col).toInt()
}

fun part1(): Int {
    var highest = 0
    // F, L = 0, B, R = 1
    input.forEach {
        val seatId = calculateSeatId(it)
        if (seatId > highest) highest = seatId
    }
    return highest
}

fun part2(): Int {
    val seatList = mutableListOf<Int>()
    // F, L = 0, B, R = 1
    input.forEach {
        seatList.add(calculateSeatId(it))
    }
    val sortedList = seatList.sorted()
    for (i in 0..sortedList.size - 2) {
        if (sortedList[i] + 1 != sortedList[i + 1]) return sortedList[i] + 1
    }
    return -1
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

