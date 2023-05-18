package com.example.owngame

import android.util.Log
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class Host {
    private var arrayClients = ArrayList<InetAddress>()
    lateinit var client: Socket
    fun runServer() {
        var running = true
        val server = ServerSocket(9999)
        while (running) {
            try {
                Log.d("CON", "server running on port ${server.localPort}")
                client = server.accept()
                Log.d("CON", "client connected ${client.inetAddress.hostAddress}")
                arrayClients.add(client.inetAddress)

            }
            catch (e: Exception) {
                e.stackTrace
            }
//создать класс крторый менеджерит клиентов
//создать арэйлист клиентов
        }
    }
    fun start(){
        var users = UserManager(client.inetAddress)
    }
}

