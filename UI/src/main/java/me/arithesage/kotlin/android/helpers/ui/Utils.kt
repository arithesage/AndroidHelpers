@file:Suppress("unused", "FunctionName", "VARIABLE_WITH_REDUNDANT_INITIALIZER",
    "LiftReturnOrAssignment"
)

package me.arithesage.kotlin.android.helpers.ui

import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout


object Utils {
    fun ChildrenOf (view: View?) : List<View> {
        val visitedNodes = ArrayList<View> ()
        val pendingNodes = ArrayList<View> ()

        if (view != null) {
            pendingNodes.add (view)

            while (pendingNodes.isNotEmpty()) {
                val child = pendingNodes.removeAt(0)
                visitedNodes.add(child)

                if (child !is ViewGroup) {
                    continue
                }

                val children = child.childCount

                for (c in 0..children) {
                    pendingNodes.add(child.getChildAt(c))
                }
            }
        }

        return visitedNodes
    }


    /**
     * Returns if we are in the UI (main) thread
     */
    fun OnUIThread (): Boolean {
        return (Looper.myLooper() == Looper.getMainLooper())
    }


    fun SetViewSize (view: View?, width: Int, height: Int) {
        if (view != null) {
            val viewContainer = view.parent
            var layoutParams: LayoutParams? = null

            when (viewContainer){
                is ConstraintLayout -> {
                    layoutParams = view.layoutParams as
                            ConstraintLayout.LayoutParams
                }

                is LinearLayoutCompat -> {
                    layoutParams = view.layoutParams as
                            LinearLayoutCompat.LayoutParams
                }

                else -> {
                    return
                }
            }

            layoutParams.width = width
            layoutParams.height = height
            view.layoutParams = layoutParams

            view.invalidate()
        }
    }
}













