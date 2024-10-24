@file:Suppress("unused")

package me.arithesage.kotlin.android.helpers.ui.prefabs

import android.content.Context
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.textfield.TextInputLayout

import me.arithesage.kotlin.android.helpers.utils.Hashing


class PasswordField (context: Context)
    : UIPrefab<LinearLayoutCompat>(context)
{
        private lateinit var passwordField: EditText
        private lateinit var passwordFieldContainer: TextInputLayout

        override fun setup () {
            ui = LinearLayoutCompat (context)

            // We use a TextInputLayout to wrap the passwordField
            // because doing things this way will automatically add a
            // 'eye' button to allow seeing the entered password.
            passwordFieldContainer = TextInputLayout (context)

            passwordField = EditText (context)
            passwordField.hint = "Password"
            passwordField.id = (0..Int.MAX_VALUE).random()
            passwordField.maxLines = 1

            passwordField.inputType = (
                    TYPE_CLASS_TEXT or
                    TYPE_TEXT_VARIATION_PASSWORD
                    )

            passwordFieldContainer.addView (passwordField)
            passwordFieldContainer.endIconMode =
                    TextInputLayout.END_ICON_PASSWORD_TOGGLE

            ui.addView (passwordFieldContainer)

            val passwordContainerParams = passwordFieldContainer.layoutParams
            passwordContainerParams.width =
                    LinearLayoutCompat.LayoutParams.MATCH_PARENT
            passwordFieldContainer.layoutParams = passwordContainerParams
        }


    /**
     * Returns the entered password hashed in SHA256
     */
    private fun generatePasswordHash(): String {
        val enteredPassword: String = passwordField.text.toString()
        return Hashing.SHA256 (enteredPassword)
    }


    fun onAccept () {
        if (passwordField.text.isNotEmpty()) {
            val hashedPassword: String = generatePasswordHash()
            passwordField.setText (hashedPassword)
        }
    }


    fun password (): String {
        return passwordField.text.toString ()
    }


    fun passwordField (): EditText {
        return passwordField
    }
}