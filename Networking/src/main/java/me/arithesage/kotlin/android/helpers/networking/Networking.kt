@file:Suppress("DEPRECATION", "FunctionName", "UNUSED", "MemberVisibilityCanBePrivate",
    "MoveLambdaOutsideParentheses", "UnusedImport"
)

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
import java.net.NetworkInterface


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


    fun CurrentIPAddress (onRetrieve: (IPAddress?) -> Unit) {
        val netTask = Task (
            {
                val netInterface = MainNetInterface()

                var ipv4: Inet4Address? = null
                var ipv6: Inet6Address? = null

                if (netInterface != null) {
                    val inetAddresses = InetAddressesFrom (netInterface)

                    for (address: InetAddress in inetAddresses) {
                        if (address is Inet4Address) {
                            ipv4 = address
                        } else if (address is Inet6Address) {
                            ipv6 = address
                        }
                    }

                    onRetrieve (IPAddress (ipv4, ipv6))
                }
            }
        )

        AsyncRunner.Do (netTask)
    }


    /*
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
    }*/


    fun InetAddressesFrom (netInterface: NetworkInterface): Array<InetAddress> {
        val inetAddresses = mutableListOf<InetAddress>()

        val netInterfaceAddresses = netInterface.inetAddresses

        while (netInterfaceAddresses.hasMoreElements()) {
            val address = netInterfaceAddresses.nextElement()
            inetAddresses.add (address)
        }

        return inetAddresses.toTypedArray()
    }


    fun Interfaces (): Array<NetworkInterface> {
        val netInterfaces = mutableListOf<NetworkInterface>()
        val interfaces = NetworkInterface.getNetworkInterfaces()

        while (interfaces.hasMoreElements()) {
            val netInterface = interfaces.nextElement()
            netInterfaces.add (netInterface)
        }

        return netInterfaces.toTypedArray()
    }


    fun MainNetInterface (): NetworkInterface? {
        val netInterfaces = Interfaces()

        for (netInterface in netInterfaces) {
            val inetAddresses = netInterface.inetAddresses

            while (inetAddresses.hasMoreElements()) {
                val inetAddress = inetAddresses.nextElement()

                if (inetAddress.isSiteLocalAddress) {
                    return netInterface
                }
            }
        }

        return null
    }
}

































