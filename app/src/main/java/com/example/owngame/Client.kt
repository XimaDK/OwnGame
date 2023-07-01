package com.example.owngame

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import android.graphics.Color
import androidx.core.view.isVisible


class Client(private val textView: TextView, private val clickToAnswer: Button) {

    private lateinit var client: Socket
    private lateinit var input: BufferedReader
    private lateinit var output: PrintWriter
    private lateinit var handler: Handler
    private var yourNickname = ""
    private var globalNickname = ""

    fun connectClient(ip: String, PORT: Int) {
        handler = Handler(Looper.getMainLooper())
        client = Socket(ip, PORT)
        input = BufferedReader(InputStreamReader(client.inputStream))
        output = PrintWriter(client.getOutputStream(), true)
        startListening()
    }

    fun setNickname(nickname: String) {
        yourNickname = nickname
    }

    private fun startListening() {
        Thread {
            try {
                while (true) {
                    val message = input.readLine()
                    if (message != null) {
                        Log.d("CON", "Клиенту пришло сообщение: $message")
                        catchMessage(message)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun sendToHost(message: String, nickname: String) {
        val fullMessage = "$nickname:$message"
        Thread {
                output.println(fullMessage)
        }.start()
    }

    private fun catchMessage(message: String) {
        if (message == "StartGame") {
            handler.post {
                textView.text = "Игра началась!"
                clickToAnswer.setBackgroundColor(Color.RED)
            }
        }

        if (message.startsWith("FirstPlayer")) {
            val nickname = message.substringAfter("FirstPlayer:")
            globalNickname = nickname
            val infoMessage = "Игрок $nickname первым нажал кнопку"
            handler.post {
                textView.text = infoMessage
                if (nickname != yourNickname) {
                    clickToAnswer.setBackgroundColor(Color.YELLOW)
                } else {
                    clickToAnswer.setBackgroundColor(Color.GREEN)
                }
                clickToAnswer.isEnabled = false
            }
        }

        if (message.startsWith("NewRound:")) {
            val roundCount = message.substringAfter("NewRound:")
            val infoMessage = "Начало раунда $roundCount"
            handler.post {
                textView.text = infoMessage
                clickToAnswer.setBackgroundColor(Color.BLUE)
                clickToAnswer.isVisible = true
                clickToAnswer.isEnabled = true
            }
        }
        if (message == "CorrectAnswer"){
            handler.post{
                textView.text = "Игрок ответил верно!"
            }
        }
        if (message == "IncorrectAnswer")
            handler.post{
                textView.text = "Игрок ответил неверно!"
                handler.post{
                    clickToAnswer.setBackgroundColor(Color.BLUE)
                    clickToAnswer.isEnabled = true
                }
                if (globalNickname == yourNickname){
                    handler.post{
                        clickToAnswer.isVisible = false
                    }
                }
            }

    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }
}

