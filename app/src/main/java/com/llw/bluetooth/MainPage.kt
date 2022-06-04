package com.llw.bluetooth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_page.*

class MainPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        button_first.setOnClickListener {
            val intent=Intent(this@MainPage,MainActivity::class.java)
            startActivity(intent)
        }

    }
}