package me.arithesage.kotlin.android.helpers.utils

import me.arithesage.kotlin.android.helpers.AsyncTask


object Utils {
    fun BytesToHex (bytes: ByteArray): String {
        return bytes.joinToString ("") {
            "%02x".format (it)
        }
    }


    fun RunAsync (task: AsyncTask) {
        android.os.AsyncTask.execute (task)
    }
}
