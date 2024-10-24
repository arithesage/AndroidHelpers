@file:Suppress("UnusedImport", "RedundantExplicitType")

package me.arithesage.kotlin.android.helpers

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import me.arithesage.kotlin.android.helpers.networking.IPAddress
import me.arithesage.kotlin.android.helpers.networking.Networking
import me.arithesage.kotlin.android.helpers.ui.Utils
import me.arithesage.kotlin.android.helpers.ui.dialogs.Requesters
import me.arithesage.kotlin.android.helpers.ui.dialogs.SimpleDialogs
import me.arithesage.kotlin.android.helpers.ui.prefabs.Terminal


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView (R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView> (R.id.textHelloWorld).visibility = View.GONE

        val testUI = LinearLayoutCompat (this)
        testUI.orientation = LinearLayoutCompat.VERTICAL
        testUI.setBackgroundColor (Color.MAGENTA)

        findViewById<ConstraintLayout>(R.id.main).addView (testUI)
        findViewById<ConstraintLayout>(R.id.main).setBackgroundColor (Color.LTGRAY)

        val dialogHelper = SimpleDialogs (this)
        val networkingHelper = Networking (this)

        val terminal = Terminal (this)
        testUI.addView (terminal.ui())

        Utils.SetViewSize (
            testUI,
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayoutCompat.LayoutParams.MATCH_PARENT
        )

        terminal.writeLn ("Hello world!")
        terminal.writeLn ("Bye!")

        Log.i ("NONE", "Finished")
    }
}















