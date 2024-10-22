@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package me.arithesage.kotlin.android.helpers.networking

import java.net.Inet4Address
import java.net.Inet6Address


class IPAddress (inetIPv4: Inet4Address?, inetIPv6: Inet6Address?) {
    var ipv4: String = ""
    var ipv6: String = ""

    init {
        if (inetIPv4 != null) {
            ipv4 = inetIPv4.hostAddress!!
        }

        if (inetIPv6 != null) {
            ipv6 = inetIPv6.hostAddress!!
        }
    }


    fun ipv4 (): String {
        return ipv4
    }


    fun ipv6 (): String {
        return ipv6
    }
}
