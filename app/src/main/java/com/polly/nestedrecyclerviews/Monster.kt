package com.polly.nestedrecyclerviews

sealed class Row

data class Monster(val name: String) : Row()

data class Horde(val monsters: List<Monster>, val hoistedToTopLevel: Boolean = false, val name: String = "") : Row()

