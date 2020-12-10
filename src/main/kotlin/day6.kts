import util.readFile
import java.util.*

val input = readFile("day6.txt")
val asciiOffset = 97

fun part1(): Int {
    var bits = BitSet(26)
    var sum = 0
    for (line in input) {
        if (line.isBlank()) {
            sum += bits.cardinality()
            bits = BitSet(26)
        } else {
            line.forEach {
                bits.set(it.toInt() - asciiOffset)
            }
        }
    }
    sum += bits.cardinality()

    return sum
}

fun part2(): Int {
    var bits = mutableListOf<BitSet>()
    var sum = 0
    for (line in input) {
        if (line.isBlank()) {
            val testBits = BitSet(26)
            testBits.set(0, testBits.size() - 1) // set all bits to 1
            bits.forEach {
                testBits.and(it)
            }
            sum += testBits.cardinality()
            bits = mutableListOf()
        } else {
            val lineBits = BitSet(26)
            line.forEach {
                lineBits.set(it.toInt() - asciiOffset)
            }
            bits.add(lineBits)
        }
    }
    val testBits = BitSet(26)
    testBits.set(0, testBits.size() - 1);
    bits.forEach {
        testBits.and(it)
    }
    sum += testBits.cardinality()

    return sum
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

