package com.xpert.tkl.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.xpert.tkl.R

class SupporDetailtActivity : AppCompatActivity() {
    private var backpress:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suppor_detailt)
        backpress = findViewById(R.id.backbtn);

        backpress!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
