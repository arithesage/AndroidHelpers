package me.arithesage.kotlin.android.helpers.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

object Serialize {
    fun toBytes (obj: Object?) : ByteArray? {
        if (obj == null) {
            return null
        }

        val outStream = ByteArrayOutputStream ()
        val objInput: ObjectOutputStream

        objInput = ObjectOutputStream (outStream)
        objInput.writeObject (obj)
        objInput.flush ()

        val objBytes = outStream.toByteArray ()

        outStream.close ()
        objInput.close ()

        return objBytes
    }


    fun toJSON (dict: HashMap<String, Object?>?) : String? {
        if (dict == null) {
            return null
        }

        val json: String? = Json.encodeToString (dict)
        return json
    }
}
