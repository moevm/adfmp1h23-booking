package com.etu.booking.utils

import com.etu.booking.model.filter.SortingType

inline fun <T> compareBy(
    sortingType: SortingType,
    crossinline selector: (T) -> Comparable<*>?,
): Comparator<T> = when (sortingType) {
    SortingType.ASC -> compareBy(selector)
    SortingType.DESC -> compareByDescending(selector)
    else -> noOpComparator()
}

inline fun <T> Comparator<T>.thenBy(
    sortingType: SortingType,
    crossinline selector: (T) -> Comparable<*>?,
): Comparator<T> = when (sortingType) {
    SortingType.ASC -> thenBy(selector)
    SortingType.DESC -> thenByDescending(selector)
    else -> Comparator { a, b -> this@thenBy.compare(a, b) }
}

fun <T> noOpComparator() = Comparator<T> { _, _ -> 0 }
