@file:Suppress("UnusedImport", "RedundantExplicitType")

package me.arithesage.kotlin.android.helpers

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.arithesage.kotlin.android.helpers.networking.IPAddress

import me.arithesage.kotlin.android.helpers.networking.Networking

import me.arithesage.kotlin.android.helpers.ui.dialogs.Requesters
import me.arithesage.kotlin.android.helpers.ui.dialogs.SimpleDialogs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        SimpleDialogs.Init (this)
        Networking.Init (this)

        Networking.CurrentIPAddress (
            onRetrieve = {
                ipAddress: IPAddress? ->

                if (ipAddress != null) {
                    SimpleDialogs.ShowMessage(ipAddress.ipv4)
                }
            }
        )
    }
}











