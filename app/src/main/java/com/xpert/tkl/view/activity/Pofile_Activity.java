package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.constant.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class Pofile_Activity extends AppCompatActivity {
    private TextView name_txt;
    private TextView city_txt;
    private TextView date_txt;
    private TextView address_txt;
    private TextView phone_txt;
    private TextView pin_codettxt;
    private TextView emailtxt;
    private Button editprofile;
    private ImageView backpress;

    private String name;
    private String email;
    private String address;
    private String phone;
    private String pincod;
    private String name_res;
    private String email_res;
    private String address_res;
    private String city_res;
    private String aadhar_no_resl;
    private String prdate_res;
    private String class_res;
    private String phone_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pofile_);
        name_txt = findViewById(R.id.username_txt);
        address_txt = findViewById(R.id.address_txt);
        phone_txt = findViewById(R.id.phone_txt);
        pin_codettxt = findViewById(R.id.pin_txt);
        emailtxt = findViewById(R.id.email_txt);
        editprofile = findViewById(R.id.buttonedit);
        backpress = findViewById(R.id.backbtn);
        date_txt = findViewById(R.id.date_txt);
        city_txt = findViewById(R.id.city_txt);
         getwalletblance();

       editprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(),Update_Profile_Activity.class);
               intent.putExtra("name",name_res);
               intent.putExtra("mobile_no",phone_res);
               intent.putExtra("email",email_res);
               intent.putExtra("address",address_res);
               intent.putExtra("date",prdate_res);
               intent.putExtra("class",class_res);
               intent.putExtra("city",city_res);
               startActivity(intent);
           }
       });

       backpress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });


    }
    private void getwalletblance(){
       // Toast.makeText(this, ""+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), Toast.LENGTH_SHORT).show();
        final KProgressHUD progressDialog = KProgressHUD.create(Pofile_Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/profile-details?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("profile-details");
                    if(status.equals("200")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject walletjson = jsonArray.getJSONObject(i);
                            name_res = walletjson.getString("name");
                            phone_res = walletjson.getString("phone_no");
                            email_res = walletjson.getString("email");
                            address_res = walletjson.getString("address");
                            city_res = walletjson.getString("city");
                            prdate_res = walletjson.getString("date");
                            class_res = walletjson.getString("class");

                            name_txt.setText(name_res);
                            phone_txt.setText(phone_res);
                            emailtxt.setText(email_res);
                            address_txt.setText(address_res);
                            city_txt.setText(city_res);
                            date_txt.setText(prdate_res);
                            progressDialog.dismiss();

                        }



                    }else {
                        progressDialog.dismiss();
                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(Pofile_Activity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pofile_Activity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();

            }
        });
        queue.getCache().clear();

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}