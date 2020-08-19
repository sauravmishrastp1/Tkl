package com.xpert.tkl.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xpert.tkl.R
import com.xpert.tkl.constant.SharedPrefManager

class ProfileActivity : AppCompatActivity() {

    private var name_txt :TextView?= null
    private var address_txt:TextView?=null
    private var phone_txt:TextView?=null
    private var pin_codettxt:TextView?=null
    private var emailtxt:TextView?=null
    private var name:String?=null;
    private var email:String?=null
    private var address:String?=null
    private var phone:String?=null
    private var pincode:String?=null
    private var backpress:ImageView?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
//        name_txt = findViewById(R.id.username)
//        address_txt = findViewById(R.id.address_txt)
//        phone_txt = findViewById(R.id.phoneNo);
//       // pin_codettxt = findViewById(R.id.pincode_txt)
//        emailtxt = findViewById(R.id.email_et)
       backpress = findViewById(R.id.backbtn);
//
//         email = SharedPrefManager.getInstance(applicationContext).user.phone
//         name = SharedPrefManager.getInstance(applicationContext).user.name
//         address = SharedPrefManager.getInstance(applicationContext).user.email
//
//         name_txt!!.text=name
//        address_txt!!.text=address
//        phone_txt!!.text=email
//        pin_codettxt!!.text="201310"

        backpress!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }})
    }
}
