package com.example.owngame

import android.os.Handler
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class NetworkController(
    private val socket: Socket,
    private val handler: Handler
) {

    private val output = PrintWriter(socket.getOutputStream(), true)
    private val input = BufferedReader(InputStreamReader(socket.inputStream))

    var onMessage: ((String) -> Unit)? = null

    private lateinit var runnable: Runnable

    init {
        runnable = Runnable {
            checkMessageFromHost()?.let { onMessage?.invoke(it) }
            handler.post(runnable)
        }
        handler.post(runnable)
    }

    private fun checkMessageFromHost(): String? {
        val message = input.readLine()
        if (!message.isNullOrEmpty()) {
            Log.d("CON", "checkMessageFromHost :  $message")
            return message
        }
        Log.d("CON", "checkMessageFromHost: nul")
        return null
    }

    fun sendToHost(s: String?){
        output.println(s)
        Log.d("CON", "Message: ${s}")
    }
}