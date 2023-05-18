package com.example.owngame


import java.net.Socket

class Client {

    fun connectClient(PORT : Int) {
        val client = Socket("172.20.10.2", PORT)
            //"172.20.10.3"
    }
}