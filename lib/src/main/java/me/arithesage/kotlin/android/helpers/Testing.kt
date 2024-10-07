package me.arithesage.kotlin.android.helpers

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * For testing functions that will be included in their appropiate
 * packages later
 */
object Testing {
    var appContext: Context? = null


    fun Init (appContext: Context?): Boolean {
        if (appContext == null) {
            return false
        }

        this.appContext = appContext
        return true
    }


    fun Initialized (): Boolean {
        return (appContext != null)
    }


    fun AppName () : String  {
        if (!Initialized()) {
            return "Unknown"
        }

        val appContext = (this.appContext as Context)

        val appInfo: ApplicationInfo = appContext.applicationInfo
        val stringId: Int = appInfo.labelRes

        if (stringId != 0) {
            return appContext.getString (stringId)

        } else {
            return appInfo.nonLocalizedLabel.toString()
        }
    }
}







