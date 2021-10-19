package com.example.myapplication

class Txt {

    fun fil(str: String): List<String> {
        return str.lowercase().split("? ",". ",", "," ").filter { e -> e[0] == e[e.length-1] && e.length>1}
    }
}