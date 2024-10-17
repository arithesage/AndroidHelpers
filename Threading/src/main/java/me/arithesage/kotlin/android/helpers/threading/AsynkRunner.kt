@file:Suppress("DEPRECATION", "FunctionName", "unused")

package me.arithesage.kotlin.android.helpers.threading

import android.os.AsyncTask


/**
 * Performs tasks asynchronously
 */
object AsyncRunner {
    fun Do (task: Task) {
        AsyncTask.execute (task)
    }
}