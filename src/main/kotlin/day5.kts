import util.readFile
import kotlin.math.abs
import kotlin.math.pow

val input = readFile("day5.txt")
val seatSet = buildSeatSet()

// F, L = 0, B, R = 1
fun buildSeatSet(): Set<Int> {
    val seatSet = mutableSetOf<Int>()
    input.forEach {
        var row = 0.0
        var col = 0.0
        for (i in 0..6) {
            if (it[i] == 'B') row += 2.0.pow(abs(i - 6))
        }
        for (i in 7..9) {
            if (it[i] == 'R') col += 2.0.pow(abs(i - 9))
        }
        seatSet.add((row * 8 + col).toInt())
    }
    return seatSet
}

fun part1(): Int {
    return seatSet.maxOrNull()!!
}

fun part2() = (seatSet.minOrNull()!!..seatSet.maxOrNull()!!).first { !seatSet.contains(it) }


println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

