package me.arithesage.kotlin.android.helpers.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.ByteArrayInputStream
import java.io.ObjectInput
import java.io.ObjectInputStream

object Deserialize {
    fun <T: Serializable> fromBytes (byteArray: ByteArray?) : T? {
        if (byteArray == null) {
            return null
        }

        val inStream = ByteArrayInputStream (byteArray)
        val outChannel: ObjectInput

        outChannel = ObjectInputStream (inStream)
        val obj: T = outChannel.readObject() as T

        outChannel.close ()
        inStream.close ()

        return obj
    }


    inline fun <reified T: Serializable> fromJSON (json: String?) : T? {
        if (json == null) {
            return null
        }

        val obj: T = Json.decodeFromString<T> (json)
        return obj
    }
}










