@file:Suppress("DEPRECATION", "FunctionName", "UNUSED")

package me.arithesage.kotlin.android.helpers.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import java.net.DatagramSocket
import java.net.InetAddress
import java.net.Inet4Address
import java.net.Inet6Address

import me.arithesage.kotlin.android.helpers.threading.AsyncRunner
import me.arithesage.kotlin.android.helpers.threading.Task


object Networking {
    private var appContext: Context? = null
    private var connectivityManager: ConnectivityManager? = null

    fun Init (appContext: Context) {
        this.appContext = appContext

        this.connectivityManager = appContext.getSystemService (
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }


    fun Initialized (): Boolean {
        return (appContext != null)
    }


    fun Connected () : Boolean {
        if (!Initialized()) {
            return false
        }

        return (CurrentNetworkState() == NetworkInfo.State.CONNECTED)
    }


    fun CurrentNetworkState () : NetworkInfo.State? {
        if (!Initialized()) {
            return null
        }

        return connectivityManager?.activeNetworkInfo?.state
    }


    fun CurrentIPAddress (onCheck: (IPAddress) -> Unit) {
        val netTask = Task (
            {
                var inet4Address: Inet4Address? = null
                var inet6Address: Inet6Address? = null

                try {
                    val socket = DatagramSocket ()
                    socket.connect (InetAddress.getByName ("www.google.com"), 80)
        
                    val inetAddress: InetAddress = socket.localAddress

                    if (inetAddress != null) {
                        if (inetAddress is Inet4Address) {
                            inet4Address = inetAddress
    
                        } else if (inetAddress is Inet6Address) {
                            inet6Address = inetAddress
                        }
                    }

                    onCheck (IPAddress (inet4Address, inet6Address))
        
                } catch (ex: Exception) {
        
                }
            }
        )

        AsyncRunner.Do (netTask)
    }
}
