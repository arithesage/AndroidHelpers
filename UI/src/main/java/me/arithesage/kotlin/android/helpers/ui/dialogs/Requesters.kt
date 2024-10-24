@file:Suppress (
    "FunctionName",
    "MemberVisibilityCanBePrivate",
    "ObjectLiteralToLambda",
    "RedundantExplicitType",
    "RedundantSamConstructor",
    "RemoveEmptyParenthesesFromLambdaCall",
    "UNUSED",
    "UNUSED_ANONYMOUS_PARAMETER",
    "UNUSED_PARAMETER",
    "VARIABLE_WITH_REDUNDANT_INITIALIZER", "MoveLambdaOutsideParentheses"
)

package me.arithesage.kotlin.android.helpers.ui.dialogs

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.LinearLayoutCompat.LayoutParams
import androidx.constraintlayout.widget.ConstraintLayout

import me.arithesage.kotlin.android.helpers.ui.Utils
import me.arithesage.kotlin.android.helpers.ui.prefabs.LoginUI


/**
 * The Requesters object is meant to show more or less complex dialogs that
 * requests an information from the user, like a username or an integer value.
 */
class Requesters (private val context: Context) {

    /**
     * A simple requester for login data.
     *
     * The requester uses the LoginUI prefab and the password is
     * encoded in SHA-256.
     *
     * The response is a map of type {username: ..., password: ...}.
     *
     * @param requesterTitle An optional title for the dialog
     *
     * @param requesterCaption An optional message about the data being
     * requested
     *
     * @param onAccept A lambda to process the request response
     */
    fun login (requesterTitle: String?,
               requesterCaption: String?,
               onAccept: (Map<String, String>) -> Unit)
    {
        val loginUI = LoginUI (context)

        CustomRequest(
                requesterTitle,
                requesterCaption,
                loginUI.ui(),
                onAccept =
                {
                    loginUI.onAccept ()

                    onAccept (mapOf(
                            "username" to loginUI.username (),
                            "password" to loginUI.password ()
                    ))
                },
                null
        )
    }


    /**
     * Requests an integer value.
     *
     * Is the same as requesting an string and converting it to an
     * integer but using an input field that only accepts numbers
     * (opens the numeric keyboard, not the full one).
     *
     * @param requesterTitle An optional title for the dialog
     *
     * @param requesterCaption An optional message about the data being
     * requested
     *
     * @param onAccept A lambda to process the request response
     */
    fun requestInt (requesterTitle: String?,
                    requesterCaption: String?,
                    onAccept: (Int) -> Unit)
    {
        val inputField: EditText = EditText (context)
        inputField.inputType = InputType.TYPE_CLASS_NUMBER

        requestData (
            requesterTitle,
            requesterCaption,
            onAccept = {
                response: String ->

                try {
                    onAccept (response.toInt())

                } catch (ex: NumberFormatException) {
                    onAccept (-1)
                }
            },
            inputField,
            null
        )
    }


    /**
     * Shortcut for requesting a simple string
     *
     * @param requesterTitle An optional title for the dialog
     *
     * @param requesterCaption An optional message about the data being
     * requested
     *
     * @param onAccept A lambda to process the request response
     */
    fun requestString (requesterTitle: String?,
                       requesterCaption: String?,
                       onAccept: (String) -> Unit)
    {
        requestData (
            requesterTitle,
            requesterCaption,
            onAccept = { response: String ->
                onAccept (response)
            },
            null,
            null)
    }


    /**
     * Shows a dialog for requesting some data.
     *
     * By default, the dialog contains an input field that accepts
     * an string, but you can provide a custom input field, along with
     * an optional set of layout params if you wish.
     *
     * You can specify a custom input field, a custom set of layout
     * params for your input field (or the default one), or both.
     *
     * @param requesterTitle An optional title for the dialog
     * @param requesterCaption Optional info about the data being requested
     * @param onAccept A lambda to process the response if accepted
     * @param customInputField An optional custom input field to use
     * @param customInputFieldLayout An optional set of layout params to use
     */
    fun requestData (requesterTitle: String?,
                     requesterCaption: String?,
                     onAccept: (String) -> Unit,
                     customInputField: EditText?,
                     customInputFieldLayout: LayoutParams?)
    {
        // Starting dialog creation
        val request = AlertDialog.Builder (context)

        // We want to ensure that our UI will have a
        // ConstraintLayout as parent
        val uiContainer = ConstraintLayout (context)

        // Here is we start creating our dialog's UI

        // We want to show only an input field, so we are going
        // to use a LinearLayout with VERTICAL orientation to hold it.
        //
        // This is a very simple layout:
        // Each view will be placed below the previous one.
        val ui = LinearLayoutCompat (context)
        ui.orientation = LinearLayoutCompat.VERTICAL

        var inputField: EditText? = null

        // If a custom input field is provided, use it
        if (customInputField != null) {
            inputField = customInputField

        } else {
            // Here is our input field. Will only accept one line of text.
            inputField = EditText (context)
            inputField.maxLines = 1
        }

        // Now we add the field to the UI
        ui.addView (inputField)

        // Next, we add the UI to the container
        uiContainer.addView (ui)

        // And finally, we set the container as the view being
        // showed by the requester
        request.setView (uiContainer)

        // So, we have an input field parented to a linear layout with
        // vertical orientation (so, if we add more views, they will be placed
        // one below another) and the linear view is parented to a
        // constraint layout.

        // Now, after adding views to their parents, we can tweak their
        // layout params if needed. You cannot adjust a view's layout params
        // before placing it in a container.

        // To do that, we first obtain a copy of the view's layout params.
        // Note that a view's layout params object is null before being
        // parented to a container.
        val uiLayout = ui.layoutParams as ConstraintLayout.LayoutParams

        // Next, we adjust the layout params as needed.
        //
        // In this case, we want our linear layout, that holds our
        // input field, to fill the container where is placed, so,
        // we set its width and height params to match their parent ones.
        //
        // Also, we are adding a little margin to each side.
        uiLayout.width = LayoutParams.MATCH_PARENT
        uiLayout.height = LayoutParams.MATCH_PARENT
        uiLayout.leftMargin = 50
        uiLayout.rightMargin = 50

        // And finally, we apply the modified layout to the view
        ui.layoutParams = uiLayout

        // If no custom layout is provided for the input field, create the
        // default one

        var inputFieldLayout: LayoutParams? = null

        if (customInputFieldLayout != null) {
            inputFieldLayout = customInputFieldLayout

        } else {
            inputFieldLayout = inputField.layoutParams as LayoutParams
            inputFieldLayout.width = LayoutParams.MATCH_PARENT
        }

        inputField.layoutParams = inputFieldLayout

        // Now, let's complete our dialog.
        // If a title was given, set it.
        if (requesterTitle != null) {
            request.setTitle (requesterTitle)
        }

        // Then we set the caption if any was provided.
        //
        // This usually is a message telling what the requester
        // is requesting.

        if (requesterCaption != null) {
            request.setMessage(requesterCaption)
        }

        // And finally, define how the requester button will behave.
        // Here we are enabling two buttons.
        //
        // The positive one is the one located to the right and
        // will be our 'Accept' button.
        //
        // The negative button is the one located to the left and
        // will be our 'Cancel' button.
        //
        // We can also add a neutral button, that will be located
        // at the center.
        request.setPositiveButton (
            "Accept",
            object: DialogInterface.OnClickListener {
                override fun onClick (dialog: DialogInterface,
                                      whichButton: Int)
                {
                    // In this case, of a requester for strings,
                    // we pass to the function a lambda that accepts an string
                    // parameter, so we can return our response.

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

        // Now is time for creating and showing our dialog
        if (Utils.OnUIThread()) {
            request.create().show ()

        } else {
            (context as Activity).runOnUiThread ({
                request.create().show ()
            })
        }
    }


    /**
     * In this request dialog you pass the UI/form to show.
     *
     * @param requesterTitle An optional dialog title
     *
     * @param requesterCaption An optional message about the data
     * being requested.
     *
     * @param ui The UI to show. The UI is parented to a ConstraintLayout
     * container.
     *
     * @param onAccept The lambda to call when 'Accept' button is clicked
     *
     * @param uiLayoutParams An optional set of layout params to apply
     * to the ui.
     */
    fun CustomRequest (requesterTitle: String?,
                       requesterCaption: String?,
                       ui: LinearLayoutCompat,
                       onAccept: () -> Unit,
                       uiLayoutParams: LayoutParams?)
    {
        val request = AlertDialog.Builder (context)
        val uiContainer = ConstraintLayout (context)

        ui.orientation = LinearLayoutCompat.VERTICAL
        uiContainer.addView (ui)
        request.setView (uiContainer)

        if (uiLayoutParams != null) {
            ui.layoutParams = uiLayoutParams

        } else {
            val formUILayout =
                    ui.layoutParams as ConstraintLayout.LayoutParams

            formUILayout.width = LayoutParams.MATCH_PARENT
            formUILayout.height = LayoutParams.MATCH_PARENT
            formUILayout.leftMargin = 50
            formUILayout.rightMargin = 50

            ui.layoutParams = formUILayout
        }

        if (requesterTitle != null) {
            request.setTitle (requesterTitle)
        }

        if (requesterCaption != null) {
            request.setMessage(requesterCaption)
        }

        request.setPositiveButton (
                "Accept",
                object: DialogInterface.OnClickListener {
                    override fun onClick (dialog: DialogInterface,
                                          whichButton: Int)
                    {
                        onAccept ()
                    }
                }
        )

        request.setNegativeButton (
                "Cancel",
                DialogInterface.OnClickListener() {
                    dialog: DialogInterface,
                    whichButton: Int ->

                    fun onClick (dialog: DialogInterface,
                                 whichButton: Int)
                    {

                    }
                }
        )

        if (Utils.OnUIThread()) {
            request.create().show ()

        } else {
            (context as Activity).runOnUiThread ({
                request.create().show ()
            })
        }
    }
}




















