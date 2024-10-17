package me.arithesage.kotlin.android.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import java.net.DatagramSocket
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
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


    /*
    private fun byteArrayToStringArray (byteArray: ByteArray,
                                        toHex: Boolean): Array<String>
    {
        val list = mutableListOf<String>()

        for (byte:Byte in byteArray) {
            if (toHex) {
                list.add (String.format("%02X", byte))

            } else {
                list.add (byte.toString())
            }
        }

        return list.toTypedArray()
    }*/


    fun Connected () : Boolean {
        if (!Initialized()) {
            return false
        }

        return (CurrentNetworkState() == NetworkInfo.State.CONNECTED)
    }


    private fun currentIPAddress (): IPAddress? {
        var ipAddress: IPAddress? = null

        var inet4Address: Inet4Address? = null
        var inet6Address: Inet6Address? = null

        val netInterface = CurrentNetworkInterface()

        if (netInterface != null) {
            val inetAddresses = netInterface.inetAddresses

            while (inetAddresses.hasMoreElements()) {
                val inetAddress = inetAddresses.nextElement()

                if (inetAddress != null) {
                    if (inetAddress is Inet4Address) {
                        inet4Address = inetAddress

                    } else if (inetAddress is Inet6Address) {
                        inet6Address = inetAddress
                    }
                }
            }

            ipAddress = IPAddress (inet4Address, inet6Address)
        }

        return ipAddress
    }


    fun CurrentNetworkInterface (): NetworkInterface? {
        var netInterface: NetworkInterface? = null

        try {
            val socket: DatagramSocket = DatagramSocket()
            socket.connect (InetAddress.getByName ("1.1.1.1"), 1000)

            netInterface = NetworkInterface.getByInetAddress (
                    socket.localAddress
            )

        } catch (ex: Exception) {

        }

        return netInterface
    }


    fun CurrentNetworkState () : NetworkInfo.State? {
        if (!Initialized()) {
            return null
        }

        return connectivityManager?.activeNetworkInfo?.state
    }


    private fun isIPv4Address (byteArray: ByteArray): Boolean {
        return (byteArray.size == 4)
    }


    private fun isIPv6Address (byteArray: ByteArray): Boolean {
        return (byteArray.size == 16)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun IP () : String {
        if (!Initialized()) {
            return ""
        }

        var ip = ""

        if (Connected()) {
            val ipAddress = currentIPAddress()

            if (ipAddress != null) {
                ip = ipAddress.ipv4
            }
        }

        return ip
    }


    /*
    private fun netAddressFromByteArray (byteArray: ByteArray,
                                         separator: String): String
    {
        val stringBuilder = StringBuilder ()

        // Ensure the received byte array has the length of a
        // IPv4 (4 bytes) or a IPv6 (16 bytes)
        if ((byteArray.size == 4) || (byteArray.size == 16)) {


        }

        return stringBuilder.toString()
    }*/
}













