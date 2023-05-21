package com.example.owngame



import java.net.InetAddress
import java.net.Socket

class Client {

    private var client = Socket()
    fun connectClient(PORT : Int) {
        client = Socket("172.20.10.2", PORT)
            //"172.20.10.3"
            //172.20.10.2
    }

    fun getIpClient(): String? {
        return client.inetAddress.hostAddress
    }
//    fun checkUserName(view: View){
//        var r =
//    }
}