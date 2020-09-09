package com.xpert.tkl.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.constant.SharedPrefManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update_Profile_Activity extends AppCompatActivity {
    private ImageView bacbress;
     private EditText nameEt;
     private EditText phone_noEt;
     private EditText whatsappnoEt;
     private EditText addressEt;
     private EditText amountDoneEt;
     private EditText subjectIntrestEt;
     private EditText  classet;
     private EditText cityet;
     private EditText dob_et;
     private String name,phone,whtasppno,address,subject,amount,classEt,city_,dob;
     private String city="";
     private Button submit_details;
     private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        bacbress = findViewById(R.id.backbtn);
        nameEt = findViewById(R.id.nameEt);
       // cityet = findViewById(R.id.city_et);
        phone_noEt = findViewById(R.id.mobilenoEt);
        dob_et = findViewById(R.id.dob_et);
        whatsappnoEt = findViewById(R.id.whatsappNoEt);
        addressEt = findViewById(R.id.addressEt);
        whatsappnoEt = findViewById(R.id.whatsappNoEt);
        //subjectIntrestEt = findViewById(R.id.SubjectEttt);
        amountDoneEt = findViewById(R.id.city_et);
        submit_details = findViewById(R.id.sbumitnow);
        classet = findViewById(R.id.class_txt);

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        bacbress.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bundle = getIntent().getExtras();

        name= bundle.getString("name");
        whtasppno=bundle.getString("email");
        phone=bundle.getString("email");
        address= bundle.getString("address");
        subject= bundle.getString("date");
        amount= bundle.getString("address");
        classEt= bundle.getString("class");
        nameEt.setText(name);
        whatsappnoEt.setText(whtasppno);
        phone_noEt.setText(phone);
        addressEt.setText(address);
        dob_et.setText(subject);
        amountDoneEt.setText(amount);
        classet.setText(classEt);



    }
    private void validate(){
         name = nameEt.getText().toString();
         whtasppno = whatsappnoEt.getText().toString();
         phone = phone_noEt.getText().toString();
         address = addressEt.getText().toString();
         subject = dob_et.getText().toString();
         amount = amountDoneEt.getText().toString();
         classEt = classet.getText().toString();

         if(name.isEmpty()){
             nameEt.setError("Enter user name");
             nameEt.setFocusable(true);
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(nameEt);
         }else if(phone.isEmpty()){
             phone_noEt.setError("Enter Phone");
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(phone_noEt);

         }else if(whtasppno.isEmpty()){
             whatsappnoEt.setError("Enter email");
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(whatsappnoEt);
         }else if(amount.isEmpty()){
             amountDoneEt.setError("Enter address");
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(amountDoneEt);

         }else if(address.isEmpty()){
             addressEt.setError("Enter address");
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(addressEt);

         }else if(subject.isEmpty()){
             dob_et.setError("Enter DOB");
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(dob_et);

//         }else  if(subject.isEmpty()){
//             subjectIntrestEt.setError("Enter subject");
//             subjectIntrestEt.setFocusable(true);
//             YoYo.with(Techniques.Shake)
//                     .duration(500)
//                     .repeat(0)
//                     .playOn(subjectIntrestEt);
//
        }
         else if(classEt.isEmpty()){
             classet.setError("Enter class");
             classet.setFocusable(true);
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(classet);
         }else {
             addapotiment();
         }

    }
    private void addapotiment(){
        final KProgressHUD progressDialog = KProgressHUD.create(Update_Profile_Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/update-profile?name="+name+"&phone_no="+phone+"&email="+whtasppno+"&address="+address+"city="+amount+"&password=abhishek11@gmail.com&date="+dob+"&class="+classEt+"&user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("200")){
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                        tv.setText("Profile update add sucessfully");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        progressDialog.dismiss();

                    }else {
                        Toast.makeText(Update_Profile_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(Update_Profile_Activity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Profile_Activity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
//        @Override
//        protected Map getParams()
//        {
//            Map params = new HashMap();
//            params.put("name", name);
//            params.put("phone_no", phone);
//            params.put("address", address);
//            params.put("city",amount);
//            params.put("password","123456");
//            params.put("date",dob);
//            params.put("class",classEt);
//            params.put("id",SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
//
//            return params;
//        }

    };
        queue.getCache().clear();

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}