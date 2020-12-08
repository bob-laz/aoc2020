import util.readFile

private fun part1(): Int {
    val input = readFile("day2.txt")
    val pwList = parseInput(input)
    // for each pw string, count occurrences of character in string, check if num occurrences is in range min..max
    // if in range, increment counter
    var counter = 0
    pwList.forEach {
        if (countOccurrences(it.str, it.char) in it.val1..it.val2) counter++
    }
    return counter;
}

private fun part2(): Int {
    val input = readFile("day2.txt")
    val pwList = parseInput(input)
    // for each pw string, check whether exactly one of pw[min-1] or pw[max-1] == char but not both, if so, increment counter
    var counter = 0
    pwList.forEach {
        if (validate(it)) counter++
    }
    return counter
}

private fun validate(passwd: Password): Boolean {
    if ((passwd.str[passwd.val1 - 1] == passwd.char && passwd.str[passwd.val2 - 1] != passwd.char) ||
        (passwd.str[passwd.val1 - 1] != passwd.char && passwd.str[passwd.val2 - 1] == passwd.char)
    ) return true
    return false
}

private fun countOccurrences(s: String, ch: Char): Int {
    return s.filter { it == ch }.count()
}

private fun parseInput(input: List<String>): List<Password> {
    val pwList = mutableListOf<Password>()
    input.forEach {
        val splitLine = it.split(" ")
        val minMax = splitLine[0].split("-")
        pwList.add(Password(minMax[0].toInt(), minMax[1].toInt(), splitLine[1].replace(":", "").single(), splitLine[2]))
    }
    return pwList
}

data class Password(val val1: Int, val val2: Int, val char: Char, val str: String)

fun main() {
    println("Day 2")
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}
