package com.example.owngame


import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.io.*
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlin.collections.ArrayList


class Host {

    private val clientThread = HandlerThread("clientThread").apply { start() }

    private var arrayClients = ArrayList<NetworkController>()

    fun runServer() {
        var running = true
        val server = ServerSocket(9999)
        while (running) {
            try {
                Log.d("CON", "server running on port ${server.localPort}")
                val client = server.accept()
                Log.d("CON", "client connected ${client.inetAddress.hostAddress}")
                arrayClients.add(NetworkController(client, Handler(clientThread.looper)))
            }
            catch (e: Exception) {
                e.stackTrace
            }
        }
    }
    fun startGame() {
        for (controller in arrayClients) {
            Log.d("CON", "->  $controller")
            controller.onMessage = { message -> catchMessage(controller, message) }

        }

    }

    fun catchMessage(controller: NetworkController, message: String) {

    }


}

