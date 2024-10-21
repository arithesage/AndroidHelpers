@file:Suppress("DEPRECATION", "FunctionName", "unused")

package me.arithesage.kotlin.android.helpers.threading

import android.os.AsyncTask
import android.os.Debug


/**
 * Performs tasks asynchronously
 */
object AsyncRunner {
    fun Do (task: () -> Unit) {
        Thread (Runnable {
            if (Debug.isDebuggerConnected()) {
                Debug.waitForDebugger()
            }

            task ()
        }).start ()
    }
}