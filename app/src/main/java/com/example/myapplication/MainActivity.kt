package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val str = "aya hello, noon. my uncle is very good. My dad is engineer"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val start = findViewById<TextView>(R.id.startTextView)
        start.text = str
    }
    fun count(view: View) {
        val end = findViewById<TextView>(R.id.endTextView)
        end.text=""
        Txt().fil(str).forEach{ e -> end.append("$e ")}
    }
}
