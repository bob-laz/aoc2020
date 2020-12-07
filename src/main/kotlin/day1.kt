import util.readFile

fun part1() {
    // a + b = 2020
    // a = 2020 - b
    val input = readFile("day1.txt")
    // for each number, subtract form 2020 and then search the list for that value
    for (i in input) {
        val target = 2020 - i
        for (j in input) {
            if (target == j) {
                println(i * j)
                return
            }
        }
    }
}

fun part2() {
    // a + b + c = 2020
    // b + c = 2020 - a
    // b + c = remainingSum
    // c = remainingSum - b
    val input = readFile("day1.txt")
    for (a in input) {
        // hash set used because we can do fast lookups by hash instead of linear traversal of list with 3 for loops
        val hashSet = HashSet<Int>()
        val remainingSum = 2020 - a
        for (b in input) {
            if (hashSet.contains(remainingSum - b)) {
                println(a * b * (remainingSum - b))
                return
            }
            hashSet.add(b)
        }
    }
}


fun main() {
    //part1()
    part2()
}
