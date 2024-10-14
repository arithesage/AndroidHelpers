package me.arithesage.kotlin.android.helpers.utils

object Utils {
    fun BytesToHex (bytes: ByteArray): String {
        return bytes.joinToString ("") {
            "%02x".format (it)
        }
    }
}
