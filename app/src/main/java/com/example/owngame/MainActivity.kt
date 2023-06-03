package com.example.owngame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.owngame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var welcomeLayout : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeLayout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(welcomeLayout.root)

        val btnHost: Button = findViewById(R.id.host)
        btnHost.setOnClickListener {
            val userName = welcomeLayout.username
            val hostLayout = Intent(this, LobbyActivity::class.java)
            hostLayout.putExtra("name", userName.text.toString())
            startActivity(hostLayout)
        }

        val btnClient = findViewById<Button>(R.id.player)
        btnClient.setOnClickListener {
            val ipAddress = welcomeLayout.ipAddress
            val userName = welcomeLayout.username
            val lobbyLayout = Intent(this, PlayerLobby::class.java)
            lobbyLayout.putExtra("name", userName.text.toString())
            lobbyLayout.putExtra("ip", ipAddress.text.toString())
            startActivity(lobbyLayout)
        }
    }


}