package com.example.owngame


import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Button
import androidx.core.view.isVisible
import java.net.ServerSocket
import kotlin.collections.ArrayList


class Host(private val correctButton: Button,
            private val incorrectButton: Button){
    init {
        setAnswerButtons()
    }

    private val clientThread = HandlerThread("clientThread").apply { start() }
    private lateinit var networkController: NetworkController
    private var arrayClients = ArrayList<NetworkController>()
    private var firstPlayer: String? = null
    private var roundCount: Int = 1

    private fun setAnswerButtons() {
        correctButton.isVisible = true
        incorrectButton.isVisible = true
        correctButton.setOnClickListener {
            markAnswerAsCorrect()
        }

        incorrectButton.setOnClickListener {
            markAnswerAsIncorrect()
        }
    }
    private fun markAnswerAsCorrect() {
        for (controller in arrayClients){
            controller.onMessage = { message -> catchMessage(controller, message) }
            Thread {
                controller.sendToHost("CorrectAnswer")
                Log.d("CON", "OTVET OK")
            }.start()
        }
    }

    private fun markAnswerAsIncorrect() {
        for (controller in arrayClients){
            controller.onMessage = { message -> catchMessage(controller, message) }
            Thread {
                controller.sendToHost("IncorrectAnswer")
                Log.d("CON", "OTVET NEOK")
            }.start()
        }
    }


    fun runServer() {
        var running = true
        val server = ServerSocket(9999)
        while (running) {
            try {
                Log.d("CON", "server running on port ${server.localPort}")
                val client = server.accept()
                Log.d("CON", "client connected ${client.inetAddress.hostAddress}")
                val networkController = NetworkController(client,
                    Handler(
                            HandlerThread("clientThread: ${client.inetAddress}:${client.port}")
                                .apply { start() }.looper))
                arrayClients.add(networkController)



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
        Log.d("CON", "Количество клиентов: ${arrayClients.size}")

    }
    fun nextRound() {
        firstPlayer = null
        roundCount++
        for (client in arrayClients) {
            Thread {
                synchronized(client) {
                    client.sendToHost("NewRound:$roundCount")
                }
            }.start()
        }
    }


    private fun catchMessage(controller: NetworkController, message: String) {
        val parts = message.split(":")
        if (parts.size == 2) {
            val senderNickname = parts[0]
            val content = parts[1]

            if (content == "ClickToAnswer" && firstPlayer == null) {
                firstPlayer = senderNickname
                Log.d("CON", "Команда ClickToAnswer от клиента $senderNickname")
                for (clients in arrayClients) {
                    Thread {
                            clients.sendToHost("FirstPlayer:$senderNickname")
                    }.start()
                }
            }
        }
    }


}
