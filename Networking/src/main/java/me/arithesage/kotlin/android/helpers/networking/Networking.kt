@file:Suppress("DEPRECATION", "FunctionName", "UNUSED", "MemberVisibilityCanBePrivate",
    "MoveLambdaOutsideParentheses", "UnusedImport", "IfThenToSafeAccess", "RedundantExplicitType", "SpellCheckingInspection",
    "JoinDeclarationAndAssignment"
)

package me.arithesage.kotlin.android.helpers.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import java.net.InetAddress
import java.net.Inet4Address
import java.net.Inet6Address

import me.arithesage.kotlin.android.helpers.threading.AsyncRunner
import me.arithesage.kotlin.android.helpers.ui.dialogs.SimpleDialogs
import java.io.IOException
import java.io.InputStream

import java.net.NetworkInterface
import java.net.Socket
import java.net.URL
import java.util.Scanner


/**
 * Networking helper
 * @param context The activity where we are
 */
class Networking (private val context: Context) {
    private var connectivityManager: ConnectivityManager

    init {
        connectivityManager = context.getSystemService (
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }


    fun connected () : Boolean {
        return (currentNetworkState() == NetworkInfo.State.CONNECTED)
    }


    fun currentNetworkState () : NetworkInfo.State? {
        return connectivityManager.activeNetworkInfo?.state
    }


    fun externalIPAddress (onRetrieve: (String) -> Unit) {
        AsyncRunner.Do ({
            val ipifyURL = "https://api.ipify.org"

            var netStream: InputStream? = null
            var ipAddress: String = ""

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


    fun ipAddress (onRetrieve: (IPAddress?) -> Unit) {
        AsyncRunner.Do (
            {
                val netInterface = mainNetInterface()

                var ipv4: Inet4Address? = null
                var ipv6: Inet6Address? = null

                if (netInterface != null) {
                    val inetAddresses = inetAddressesFrom (netInterface)

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


    /**
     * Returns your IP address.
     *
     * This is quickest and simplest method for obtaining it.
     * You can also call the other function of same name that returns
     * an IPAddress object, that contains both your IPv4 and IPv6 addresses.
     */
    fun ipv4Address (onRetrieve: (String) -> Unit)  {
        webConnect (
            "github.com",
            onConnect = {
                    socket ->
                onRetrieve (socket.inetAddress.hostAddress as String)
                socket.close ()
            }
        )
    }


    fun inetAddressesFrom (netInterface: NetworkInterface): Array<InetAddress> {
        val inetAddresses = mutableListOf<InetAddress>()

        val netInterfaceAddresses = netInterface.inetAddresses

        while (netInterfaceAddresses.hasMoreElements()) {
            val address = netInterfaceAddresses.nextElement()
            inetAddresses.add (address)
        }

        return inetAddresses.toTypedArray()
    }


    fun interfaces (): Array<NetworkInterface> {
        val netInterfaces = mutableListOf<NetworkInterface>()
        val interfaces = NetworkInterface.getNetworkInterfaces()

        while (interfaces.hasMoreElements()) {
            val netInterface = interfaces.nextElement()
            netInterfaces.add (netInterface)
        }

        return netInterfaces.toTypedArray()
    }


    fun mainNetInterface (): NetworkInterface? {
        val netInterfaces = interfaces()

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


    /**
     * Open a connection to a server
     *
     * @param address Server address (IP or URL)
     * @param port Port to connect
     *
     * @param onConnect What to do on connection.
     * Provides the connected socket
     */
    fun connectTo (address: String,
                   port: Int,
                   onConnect: (Socket) -> Unit)
    {
        AsyncRunner.Do {
            var connection: Socket? = null

            try {
                connection = Socket(address, port)
                onConnect (connection as Socket)

            } catch (ex: Exception) {
                SimpleDialogs (context).showMessage("Exception: " + ex.message)
            }
        }
    }


    /**
     * Do the same as 'connectTo', but defaults the port to 80
     */
    fun webConnect (url: String,
                    onConnect: (Socket) -> Unit)
    {
        connectTo (url,80, onConnect = {
            socket ->
            onConnect (socket)
        })
    }
}

































