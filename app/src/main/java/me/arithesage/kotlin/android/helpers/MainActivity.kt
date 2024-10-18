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

        var ip: String = ""

        Networking.CurrentIPAddress (
            onCheck = {
                ipAddress: IPAddress ->
                ip = ipAddress.ipv4
            }
        )

        SimpleDialogs.ShowMessage (ip)

        //val ip: String = Networking.CurrentIPAddress ()

        //SimpleDialogs.ShowMessage (ip)

        //SimpleDialogHelper.Init (this)
        //Requesters.Init (this)

        /*
        Requesters.RequestString (
            "Test",
            "Enter data:",
            onAccept = { response: String -> SimpleDialogHelper.ShowMessage (response)
            }
        )
        */
    }
}











