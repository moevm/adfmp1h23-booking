package com.etu.booking.utils

inline fun <reified T : Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

inline fun <reified T : Enum<T>> getEnumIgnoreCase(enumName: String, defaultEnum: T? = null): T? {
    val values = enumValues<T>()
    return values.firstOrNull { it.name.uppercase() == enumName.uppercase() } ?: defaultEnum
}
