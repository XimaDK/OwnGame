package com.example.owngame


import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.net.ServerSocket
import kotlin.collections.ArrayList


class Host {

    private val clientThread = HandlerThread("clientThread").apply { start() }
    private lateinit var networkController: NetworkController
    private var arrayClients = ArrayList<NetworkController>()


    fun runServer() {
        var running = true
        val server = ServerSocket(9999)
        while (running) {
            try {
                Log.d("CON", "server running on port ${server.localPort}")
                val client = server.accept()
                Log.d("CON", "client connected ${client.inetAddress.hostAddress}")
                networkController = NetworkController(client, Handler(clientThread.looper))
                arrayClients.add(NetworkController(client, Handler(clientThread.looper)))

            }
            catch (e: Exception) {
                e.stackTrace
            }
        }
    }
    fun startGame() {
        for (controller in arrayClients) {
            controller.onMessage = { message -> catchMessage(controller, message) }
            Thread {
                controller.sendToHost("StartGame")
            }.start()
        }
    }

    fun catchMessage(controller: NetworkController, message: String) {
        val parts = message.split(":")
        if (parts.size == 2) {
            val senderNickname = parts[0]
            val content = parts[1]

            if (content == "ClickToAnswer") {
                Log.d("CON", "Команда ClickToAnswer от клиента $senderNickname")
            }
            if (content == "2") {
                Log.d("CON", "Команда 2 от клиента $senderNickname")

            }
        }
    }

}
