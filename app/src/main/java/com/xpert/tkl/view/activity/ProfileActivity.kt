package com.xpert.tkl.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.xpert.tkl.Presenter.utils.VolleySingleton
import com.xpert.tkl.R
import com.xpert.tkl.constant.SharedPrefManager
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    private var name_txt: TextView? = null
    private var city_txt:TextView?=null;
    private var date_txt:TextView?=null;
    private var address_txt: TextView? = null
    private var phone_txt: TextView? = null
    private var pin_codettxt: TextView? = null
    private var emailtxt: TextView? = null
    private var name: String? = null;
    private var email: String? = null
    private var address: String? = null
    private var phone: String? = null
    private var pincode: String? = null
    private var backpress: ImageView? = null
    private var editprofile: Button? = null
    private var name_res:String?=null;
    private var email_res:String?= null;
    private var address_res:String?=null;
    private var city_res:String?=null;
    private var aadhar_no_res:String?=null;
    private var prdate_res:String?=null;
    private var class_res:String?=null;
    private var phone_res:String?=null;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name_txt = findViewById(R.id.username_txt)
        address_txt = findViewById(R.id.address_txt)
        phone_txt = findViewById(R.id.phone_txt);
        pin_codettxt = findViewById(R.id.pin_txt)
        emailtxt = findViewById(R.id.email_txt)
        editprofile = findViewById(R.id.buttonedit)
        backpress = findViewById(R.id.backbtn);
        date_txt = findViewById(R.id.date_txt);
        city_txt = findViewById(R.id.city_txt);
        getprofile()
         name_txt!!.text=name_res
        address_txt!!.text=address_res
        phone_txt!!.text=phone_res
        pin_codettxt!!.text="20110"
        date_txt!!.text=prdate_res



        email = SharedPrefManager.getInstance(applicationContext).user.phone
        name = SharedPrefManager.getInstance(applicationContext).user.name
        address = SharedPrefManager.getInstance(applicationContext).user.email


        editprofile!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, Update_Profile_Activity::class.java)
                startActivity(intent)
            }
        })

        backpress!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun getprofile() {
       // Toast.makeText(this, "Data is not submit"+class_res, Toast.LENGTH_SHORT).show();
        val queue = Volley.newRequestQueue(applicationContext)
        val url = "https://tklpvtltd.com/tkl/api/profile-details?user_id="+SharedPrefManager.getInstance(applicationContext).user.id
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    Toast.makeText(this, "Data is not submit"+class_res, Toast.LENGTH_SHORT).show();
                    var jsonObject = JSONObject(response)
                    var status = jsonObject.getString("status")
                    var jsonArray = jsonObject.getJSONArray("profile-details")
                    if (status == "200") {
                        for (i in 0 until jsonArray.length()) {
                            var walletjson = jsonArray.getJSONObject(i)
                             name_res = walletjson.getString("name")
                              phone_res = walletjson.getString("phone");
                              email_res = walletjson.getString("email");
                            address_res = walletjson.getString("address");
                            city_res = walletjson.getString("city");
                            prdate_res = walletjson.getString("date");
                            class_res = walletjson.getString("class");
                            Toast.makeText(this, "Data is not submit"+class_res, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Data is not submit"+class_res, Toast.LENGTH_SHORT).show();
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "somtihing wet wrong" + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    // progressDialog.dismiss();
                }
            }) {
            Toast.makeText(this, "Server Error!!", Toast.LENGTH_SHORT).show()
            //   progressDialog.dismiss();
        }
        queue.cache.clear()
        VolleySingleton.getInstance(applicationContext).requestQueue.cache.clear()
        request.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        VolleySingleton.getInstance(applicationContext).addToRequestQueue(request)
    }
}

