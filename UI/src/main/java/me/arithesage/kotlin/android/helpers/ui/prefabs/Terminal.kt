@file:Suppress("unused")

package me.arithesage.kotlin.android.helpers.ui.prefabs

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat


class Terminal (context: Context)
    :UIPrefab<LinearLayoutCompat> (context){
    private lateinit var buffer: TextView
    private lateinit var inputField: EditText

    override fun setup() {
        ui = LinearLayoutCompat (context)

        buffer = TextView (context)
        buffer.setBackgroundColor (Color.GREEN) // For debugging
        buffer.gravity = Gravity.BOTTOM

        inputField = EditText (context)
        inputField.setBackgroundColor (Color.CYAN) // For debugging
        inputField.isCursorVisible = true
        inputField.setText ("> ")

        /*inputField.setOnEditorActionListener(
                TextView.OnEditorActionListener()
                {
                    view: TextView,
                            actionId: Int,
                            event: KeyEvent -> {

                }
                }
        )
        */

        ui.addView (buffer)
        ui.addView (inputField)

        val bufferLayoutParams = buffer.layoutParams
        bufferLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        bufferLayoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        buffer.layoutParams = bufferLayoutParams
        
        val inputLayoutParams = inputField.layoutParams
        inputLayoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        inputLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        inputField.layoutParams = inputLayoutParams
    }


    fun read (): String {
        return buffer.text.toString()
    }


    fun write (text: String) {
        buffer.append (text)
    }


    fun writeLn (text: String) {
        buffer.append (text + "\n")
    }
}
