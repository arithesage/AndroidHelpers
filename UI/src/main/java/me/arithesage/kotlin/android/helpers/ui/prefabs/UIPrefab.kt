@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package me.arithesage.kotlin.android.helpers.ui.prefabs

import android.content.Context
import android.view.ViewGroup


abstract class UIPrefab<T:ViewGroup> (protected val context: Context) {
    protected lateinit var ui: T

    init {
        setup ()
    }


    protected abstract fun setup ()


    fun ui (): T {
        return ui
    }


    /**
     * Set your prefab default layout params here if any.
     * Remember adding 'ui.invalidate ()'
     */
    open fun updateLayout () {

    }
}