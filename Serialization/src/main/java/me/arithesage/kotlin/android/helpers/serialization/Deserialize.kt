package me.arithesage.kotlin.android.helpers.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.ByteArrayInputStream
import java.io.ObjectInput
import java.io.ObjectInputStream
import java.io.InvalidClassException

@Suppress ("FINAL_UPPER_BOUND", "UNCHECKED_CAST")
object Deserialize {
    fun <T: Serializable> fromBytes (byteArray: ByteArray?) : T? {
        if (byteArray == null) {
            return null
        }

        val inStream = ByteArrayInputStream (byteArray)
        val outChannel: ObjectInput

        outChannel = ObjectInputStream (inStream)

        try {
            val obj: T = (outChannel.readObject() as T)
            return obj

        } catch (ex: InvalidClassException) {
            return null
            
        } finally {
            outChannel.close ()
            inStream.close ()
        }
    }


    inline fun <reified T: Serializable> fromJSON (json: String?) : T? {
        if (json == null) {
            return null
        }

        try {
            val obj: T = Json.decodeFromString<T> (json)
            return obj

        } catch (ex: InvalidClassException) {
            return null
        }
    }
}










