@file:Suppress("ImplicitThis")

package me.arithesage.kotlin.android.helpers.threading

import android.os.Debug


/**
 * Something to do
 *
 * @param action Action (lambda) to execute. 
 */
class Task (action: () -> Unit) : Runnable
{
    val __action = action
    
    override fun run() {
        if (Debug.isDebuggerConnected()) {
            Debug.waitForDebugger()
        }

        __action ()
    }
}