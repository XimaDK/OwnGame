package com.example.owngame

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.math.log

class Client(private val textView: TextView) {

    private lateinit var client: Socket
    private lateinit var input: BufferedReader
    private  lateinit var output : PrintWriter
    private lateinit var handler : Handler


    fun connectClient(PORT: Int) {
        handler = Handler(Looper.getMainLooper())
        client = Socket("172.20.10.7", PORT)
        input = BufferedReader(InputStreamReader(client.inputStream))
        output = PrintWriter(client.getOutputStream(), true)
        startListening()
        //"172.20.10.3"
        //172.20.10.2
    }

    fun startListening() {
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

    fun catchMessage(message: String) {

        if (message == "StartGame") {
            handler.post {
                textView.text = "Игра началалась!"}
        }
        if (message == "2"){
            Log.d("CON", "От хоста к клиенту пришла команда 2")
        }
    }


    fun setHandler(handler: Handler) {
        this.handler = handler
    }

}