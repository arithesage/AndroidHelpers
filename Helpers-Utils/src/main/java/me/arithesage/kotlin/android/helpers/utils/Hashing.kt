@file:Suppress("RedundantExplicitType", "unused", "FunctionName")

package me.arithesage.kotlin.android.helpers.utils

import java.security.MessageDigest

import me.arithesage.kotlin.android.helpers.serialization.Serialize


object Hashing {
    /**
     * Obtain a SHA-256 hash from an object
     */
    fun SHA256 (obj: Any?): String {
        var hash: String = ""

        if (obj != null) {
            val objBytes: ByteArray? = Serialize.toBytes (obj)

            if (objBytes != null) {
                val digest = MessageDigest.getInstance ("SHA-256").digest (
                    objBytes
                )
                
                hash = Utils.BytesToHex (digest)
            }
        }

        return hash
    }
}
