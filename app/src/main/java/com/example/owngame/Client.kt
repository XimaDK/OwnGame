package com.example.owngame

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.math.log

class Client {

    private lateinit var client : Socket
    private lateinit var networkController : NetworkController
    private lateinit var output : PrintWriter

    fun connectClient(PORT : Int) {
        Handler(Looper.getMainLooper())
        client = Socket("10.114.7.45", PORT)
            //"172.20.10.3"
            //172.20.10.2
    }



//    fun sendToHost(s: String){
//        output = PrintWriter(client.getOutputStream(), true)
//        output.println(s)
//        Log.d("CON", "Клиент отправил ${s}")
//    }




}