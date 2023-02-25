package com.etu.booking.control

fun <T, R : Comparable<R>> sort(sort: Int, list: List<T>, selector: (T) -> R) = when(sort) {
    1 -> list.sortedBy(selector = selector)
    2 -> list.sortedByDescending(selector = selector)
    else -> list
}
