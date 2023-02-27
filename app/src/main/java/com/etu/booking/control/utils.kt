package com.etu.booking.control

import android.content.Context
import com.etu.booking.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun <T, R : Comparable<R>> sort(sort: Int, list: List<T>, selector: (T) -> R) = when (sort) {
    1 -> list.sortedBy(selector = selector)
    2 -> list.sortedByDescending(selector = selector)
    else -> list
}

fun createFile(baseFolder: File, format: String, extension: String) =
    File(
        baseFolder, SimpleDateFormat(format, Locale.US)
            .format(System.currentTimeMillis()) + extension
    )


fun Context.getOutputDirectory(): File {
    val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
        File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else this.filesDir
}