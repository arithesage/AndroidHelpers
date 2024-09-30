@file:Suppress (
    "FunctionName",
    "MemberVisibilityCanBePrivate",
    "NAME_SHADOWING",
    "ObjectLiteralToLambda",
    "RedundantSamConstructor",
    "RemoveEmptyParenthesesFromLambdaCall",
    "SENSELESS_COMPARISON",
    "UNUSED",
    "UNUSED_ANONYMOUS_PARAMETER",
    "UNUSED_PARAMETER"
)

package me.arithesage.kotlin.android.helpers.ui

import android.content.Context
import android.content.DialogInterface
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout

class Requesters {
    private var appContext: Context? = null

    fun Init (appContext: Context) {
        if (appContext != null) {
            this.appContext = appContext
        }
    }


    fun Initialized () : Boolean {
        return (this.appContext != null)
    }


    fun RequestString (requesterTitle: String?,
                       requestCaption: String,
                       onAccept: (String) -> Unit)
    {
        if (!Initialized()) {
            return
        }

        val request = AlertDialog.Builder (appContext as Context)

        if (requesterTitle != null) {
            request.setTitle (requesterTitle)
        }

        val container = ConstraintLayout (appContext as Context)

        val rows = LinearLayout (appContext)
        rows.orientation = LinearLayout.VERTICAL

        val inputField = EditText (appContext)
        inputField.maxLines = 1
        rows.addView (inputField)

        container.addView (rows)
        container.left = 20
        container.top = 20

        val inputFieldLayout = inputField.layoutParams
        inputFieldLayout.width = LinearLayout.LayoutParams.MATCH_PARENT
        inputField.layoutParams = inputFieldLayout

        request.setMessage (requestCaption)
        request.setView (container)

        request.setPositiveButton (
            "Accept",
            object: DialogInterface.OnClickListener {
                override fun onClick (dialog: DialogInterface,
                                      whichButton: Int)
                {
                    onAccept (inputField.text.toString())
                }
            }
        )

        request.setNegativeButton (
            "Cancel",
            DialogInterface.OnClickListener() {
                    dialog: DialogInterface,
                    whichButton: Int ->

                fun onClick (dialog: DialogInterface,
                             whichButton: Int) {

                }
            }
        )

        request.show ()
    }
}