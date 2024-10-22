package me.arithesage.kotlin.android.helpers.utils

import android.os.Looper


object Utils {
    fun BytesToHex (bytes: ByteArray): String {
        return bytes.joinToString ("") {
            "%02x".format (it)
        }
    }
}
