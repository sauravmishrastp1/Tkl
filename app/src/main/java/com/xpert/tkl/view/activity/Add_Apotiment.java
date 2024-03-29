package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.Image;
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
import com.xpert.tkl.constant.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class Add_Apotiment extends AppCompatActivity {
    private ImageView bacbress;
     private EditText nameEt;
     private EditText phone_noEt;
     private EditText whatsappnoEt;
     private EditText addressEt;
     private EditText amountDoneEt;
     private EditText subjectIntrestEt;
     private String name,phone,whtasppno,address,subject,amount;
     private Button submit_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__apotiment);
        bacbress = findViewById(R.id.backbtn);
        nameEt = findViewById(R.id.nameEt);
        phone_noEt = findViewById(R.id.mobilenoEt);
        whatsappnoEt = findViewById(R.id.whatsappNoEt);
        addressEt = findViewById(R.id.addressEt);
        whatsappnoEt = findViewById(R.id.whatsappNoEt);
        subjectIntrestEt = findViewById(R.id.SubjectEt);
        amountDoneEt = findViewById(R.id.amountEt);
        submit_details = findViewById(R.id.sbumitnow);
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


    }
    private void validate(){
         name = nameEt.getText().toString();
         whtasppno = whatsappnoEt.getText().toString();
         phone = phone_noEt.getText().toString();
         address = addressEt.getText().toString();
         subject = subjectIntrestEt.getText().toString();
         amount = amountDoneEt.getText().toString();
         if(name.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(nameEt);
             nameEt.setError("Enter user name");
             nameEt.setFocusable(true);
         }else if(whtasppno.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(whatsappnoEt);
             whatsappnoEt.setError("Enter whatsapp no");

         }else if(phone.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(phone_noEt);
             phone_noEt.setError("Enter Phone");

         }else if(address.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(addressEt);
             addressEt.setError("Enter address");


         }else  if(subject.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(subjectIntrestEt);
             subjectIntrestEt.setError("Enter subject");
             subjectIntrestEt.setFocusable(true);


         }else if(amount.isEmpty()){
             YoYo.with(Techniques.Shake)
                     .duration(500)
                     .repeat(0)
                     .playOn(amountDoneEt);
             amountDoneEt.setError("Enter amount");
             amountDoneEt.setFocusable(true);

         }else {
             addapotiment();
         }

    }
    private void addapotiment(){
        final KProgressHUD progressDialog = KProgressHUD.create(Add_Apotiment.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/add-appointment?name="+name+"&mobile_no="+phone+"&whatapp_no="+whtasppno+"&subject_interested="+subject+"&address="+address+"&amount="+amount;
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
                        tv.setText("Appointment add sucessfully");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        progressDialog.dismiss();

                    }else {
                        Toast.makeText(Add_Apotiment.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(Add_Apotiment.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Apotiment.this, "Server Error!!", Toast.LENGTH_SHORT).show();
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