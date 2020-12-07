package util

fun readFile(fileName: String) =
    object {}.javaClass.getResourceAsStream(fileName).bufferedReader().readLines().map { it.toInt() }
