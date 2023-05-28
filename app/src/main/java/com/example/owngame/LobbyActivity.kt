package com.example.owngame

import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import com.example.owngame.databinding.ActivityLobbyBinding


class LobbyActivity : AppCompatActivity() {

    private lateinit var lobbyLayout: ActivityLobbyBinding
    private lateinit var con: Host

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lobbyLayout = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(lobbyLayout.root)

        val nickname = intent.getStringExtra("name")
        lobbyLayout.username.text = nickname.toString()

        con = Host()
        Thread { con.runServer() }.start()

        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ipAddress: String = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        lobbyLayout.ipAddress.text = ipAddress


    }

    fun startGame(view: View) {
        val button = findViewById<Button>(R.id.buttonStart)
        button.setOnClickListener {
            con.startGame()
            Log.d("CON", "OK") }
    }

}