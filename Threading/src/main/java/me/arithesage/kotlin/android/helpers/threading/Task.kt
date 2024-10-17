package me.arithesage.kotlin.android.helpers.threading

import android.os.Debug


/**
 * Something to do
 *
 * @param runnable Action (lambda) to execute
 */
class Task (runnable: Runnable, onFinish: Runnable?) : Runnable{
    private var _action: Runnable = runnable
    private var _onFinish: Runnable? = onFinish

    override fun run() {
        if (Debug.isDebuggerConnected()) {
            Debug.waitForDebugger()
        }

        _action.run()

        // If an 'onFinish' action was defined, execute it
        _onFinish?.run()
    }
}