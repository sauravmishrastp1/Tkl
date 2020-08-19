package com.xpert.tkl.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.xpert.tkl.R
import com.xpert.tkl.constant.SharedPrefManager

class ShareAndEarnActivity : AppCompatActivity() {
   private var backpress:ImageView?=null
    private var share_btn:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_and_earn)
        share_btn = findViewById(R.id.sharenowww);
        backpress = findViewById(R.id.backbtn);
        backpress!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        })
      share_btn!!.setOnClickListener(object :View.OnClickListener{

          override fun onClick(p0: View?) {
              val shareIntent = Intent(Intent.ACTION_SEND)
              shareIntent.type = "text/plain"
              shareIntent.putExtra(
                  Intent.EXTRA_SUBJECT,
                  "Referal code:=" +"Abcf123e"
              )
              val appPackageName = applicationContext.packageName
              val app_url = "https://play.google.com/store/apps/details?id=$appPackageName"
              shareIntent.putExtra(Intent.EXTRA_TEXT, app_url)
              startActivity(Intent.createChooser(shareIntent, "Share via"))
          }

      })
    }
}
