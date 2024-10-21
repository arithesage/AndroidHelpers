@file:Suppress("DEPRECATION", "FunctionName", "UNUSED", "MemberVisibilityCanBePrivate",
    "MoveLambdaOutsideParentheses", "UnusedImport", "IfThenToSafeAccess", "RedundantExplicitType"
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
import java.io.IOException
import java.io.InputStream

import java.net.NetworkInterface
import java.net.URL
import java.util.Scanner


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
        AsyncRunner.Do (
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
    }


    fun ExternalIPAddress (onRetrieve: (String) -> Unit) {
        AsyncRunner.Do ({
            var netStream: InputStream? = null
            var ipAddress: String = ""
            val ipifyURL = "https://api.ipify.org"

            try {
                netStream = URL (ipifyURL).openStream()

                val streamReader = Scanner(netStream, "UTF-8")
                ipAddress = streamReader.next()

            } catch (_: IOException) {

            } finally {
                if (netStream != null) {
                    netStream.close()
                }
            }

            onRetrieve (ipAddress)
        })
    }


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

































