package com.xpert.tkl.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SinupActivity extends AppCompatActivity {
  private EditText username_Et;
  private EditText email_Et;
  private EditText password_Et;
  private EditText phone_Et;
  private ImageView Sinup;
  private ProgressBar progressBar;
  private View login_txt;
  private String user_id;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);
        username_Et = findViewById(R.id.username_et);
        email_Et = findViewById(R.id.email_et);
        password_Et = findViewById(R.id.password_et);
        phone_Et = findViewById(R.id.phone_et);
        Sinup = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progresbbar);
        login_txt = findViewById(R.id.register_layout);

        Sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateuser();
            }
        });

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SinupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void  validateuser(){

        String username = username_Et.getText().toString();
         email = email_Et.getText().toString();
        String password = password_Et.getText().toString();
        String phone = phone_Et.getText().toString();

        if(username.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(username_Et);
            username_Et.setError("Enter user name");
            username_Et.setFocusable(true);

        }else if(email.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(email_Et);
            email_Et.setError("Enter email");
            email_Et.setFocusable(true);
        }else if(!email.matches(emailPattern)){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(email_Et);
            email_Et.setError("Please enter valid email");
            email_Et.setFocusable(true);
        }else if(phone.length()<10||phone.length()>10){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(phone_Et);
            phone_Et.setError("Phone must be 10 digits");
            phone_Et.setFocusable(true);}
        else if(password.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(password_Et);
            password_Et.setError("Enter Password");
            password_Et.setFocusable(true);
        }else {
            sinupApi(username,email,phone,password);
        }

    }

    private void sinupApi(String userName ,String userEmail,String userPhone,String userpassword ){

        final KProgressHUD progressDialog = KProgressHUD.create(SinupActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.SINUP_URL+"name="+userName+"&email="+userPhone+"&password="+userpassword+"&type="+"1",
                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);

                            String status = object.getString("status");
                            JSONObject userJson = object.getJSONObject("user");

                            if (status.equals("200")) {


                                String userid = userJson.getString("id");
                                String username = userJson.getString("name");
                                String email = userJson.getString("email");
                                String user_type = userJson.getString("type");
                                User user=new User(userid,username,email, "",user_type);
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                               updatewallet();
                                progressDialog.dismiss();
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                                TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                                tv.setText("REGISTER SUCCESSFULLY");
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.wrong_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                                TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                                tv.setText("ALREADY REGSITER");
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                progressDialog.dismiss();
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.wrong_layout, (ViewGroup) findViewById(R.id.custom_toast_layouttt));
                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                            tv.setText("ALREADY REGSITER");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int errorCode = 0;
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Timeout !!!!Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = -7;
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No Connection !!!Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = -1;
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailure Error!!! Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = -6;
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error!!! Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = 0;
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network error !!!Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = -1;
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Server rror!!! Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            errorCode = -8;
                        }
                    }
                }) {





        };


        queue.getCache().clear();

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    private void updatewallet(){
      //  Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="https://tklpvtltd.com/tkl/api/update-user-wallet?user_id=1&money=0";
        String url ="https://tklpvtltd.com/tkl/api/add-wallet?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money=0";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    //  JSONArray jsonArray = jsonObject.getJSONArray("$data");
                    if(status.equals("200")){

                        //Toast.makeText(SinupActivity.this, "Data", Toast.LENGTH_SHORT).show();


                    }else {
                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                   // Toast.makeText(SinupActivity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(SinupActivity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();

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




