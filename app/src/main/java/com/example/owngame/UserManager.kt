package com.example.owngame

import android.util.Log
import android.view.View
import java.net.InetAddress

class UserManager() {

    fun listUsers(users : ArrayList<InetAddress>){
        val range = users.size
        for (i in 1..range){
            Log.d("CON", "ss${users[i]}")
        }

    }
}