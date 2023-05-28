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

    fun connectClient(PORT : Int) {
        Handler(Looper.getMainLooper())
        client = Socket("172.20.10.6", PORT)
        //"172.20.10.3"
            //172.20.10.2
    }

}