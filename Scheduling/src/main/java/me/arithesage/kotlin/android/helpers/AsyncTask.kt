package me.arithesage.kotlin.android.helpers

import android.os.Debug


class AsyncTask (runnable: Runnable) : Runnable {
    private lateinit var _action: Runnable

    init {
        _action = runnable
    }

    override fun run() {
        if (Debug.isDebuggerConnected()) {
            Debug.waitForDebugger()
        }

        _action.run()
    }
}
