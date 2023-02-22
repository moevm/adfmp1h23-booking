package com.etu.booking.control

import android.content.Intent
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

fun <T : Serializable> addSerializableToIntent(name: String, model: T, intent: Intent) {
    intent.putExtra(name, model.toByteArray())
}

fun <T : Serializable> readFromIntent(name: String, intent: Intent): T =
    fromByteArray(intent.getByteArrayExtra(name)!!)

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> fromByteArray(byteArray: ByteArray): T {
    val byteArrayInputStream = ByteArrayInputStream(byteArray)
    val objectInput: ObjectInput
    objectInput = ObjectInputStream(byteArrayInputStream)
    val result = objectInput.readObject() as T
    objectInput.close()
    byteArrayInputStream.close()
    return result
}

fun Serializable.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutputStream: ObjectOutputStream
    objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
    objectOutputStream.writeObject(this)
    objectOutputStream.flush()
    val result = byteArrayOutputStream.toByteArray()
    byteArrayOutputStream.close()
    objectOutputStream.close()
    return result
}
