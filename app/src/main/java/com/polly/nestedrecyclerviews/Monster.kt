package com.polly.nestedrecyclerviews

sealed class RowType

data class Monster(val name: String) : RowType()

data class Horde(val monsters: List<Monster>, val embedded: Boolean = false) : RowType()
