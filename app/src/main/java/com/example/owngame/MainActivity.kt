package com.example.owngame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.owngame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var welcomeLayout : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        welcomeLayout = ActivityMainBinding.inflate(layoutInflater)
        setContentView(welcomeLayout.root)
    }
    fun onHostLayout(view: View){
        val userName = welcomeLayout.username
        val hostLayout = Intent(this, LobbyActivity::class.java)
        hostLayout.putExtra("name", userName.text.toString())
        startActivity(hostLayout)
    }
    fun onPlayerLobby(view: View){
        val userName = welcomeLayout.username
        val lobbyLayout = Intent(this, PlayerLobby::class.java)
        lobbyLayout.putExtra("name", userName.text.toString())
        startActivity(lobbyLayout)
    }

}