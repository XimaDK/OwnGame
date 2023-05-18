package com.example.owngame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.owngame.databinding.ActivityPlayerLobbyBinding

class PlayerLobby : AppCompatActivity() {

    private lateinit var playerLobby : ActivityPlayerLobbyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerLobby = ActivityPlayerLobbyBinding.inflate(layoutInflater)
        setContentView(playerLobby.root)
        val conn = Client()
        Thread { conn.connectClient(9999)}.start()

    }
}