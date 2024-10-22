@file:Suppress (
    "FunctionName",
    "MemberVisibilityCanBePrivate",
    "NAME_SHADOWING",
    "ObjectLiteralToLambda",
    "RedundantSamConstructor",
    "RemoveEmptyParenthesesFromLambdaCall",
        "UNUSED",
    "UNUSED_ANONYMOUS_PARAMETER",
    "UNUSED_PARAMETER"
)

package me.arithesage.kotlin.android.helpers.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


/**
 * The SimpleDialogs object is meant to dialogs like a message,
 * or a question where you only can answer yes or no.
 */
class SimpleDialogs (private val context: Context) {

    /**
     * Shows a message.
     *
     * This function is a shortcut for showing a message
     * without title and with a 'Close' button.
     */
    fun showMessage (message: String) {
        showMessage (null, message, null)
    }


    /**
     * Shows a message
     *
     * @param title The title of the dialog (null for none)
     * @param message The message to show
     *
     * @param closeButtonLabel The text to show in the close button
     *                         (null for 'Close')
     */
    fun showMessage (title: String?,
                     message: String,
                     closeButtonLabel: String?)
    {
        val dialog: AlertDialog.Builder = AlertDialog.Builder (context)

        if (title != null) {
            dialog.setTitle (title)
        }

        dialog.setMessage (message)

        dialog.setPositiveButton (
            // If a closeButtonLabel was defined is used.
            // Otherwise, 'Close' is used as label.
            closeButtonLabel ?: "Close",

            DialogInterface.OnClickListener() {
                dialog: DialogInterface,
                whichButton: Int ->
                fun onClick (dialog: DialogInterface,
                             whichButton: Int) {

                }
            }
        )

        dialog.show ()
    }
}

















