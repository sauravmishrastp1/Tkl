package com.xpert.tkl.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class  LoginActivity extends AppCompatActivity {
     private EditText username_Et;
     private EditText password_Et;
     private ImageView btn_login;
     private ProgressBar progressBar;
     private View register_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username_Et = findViewById(R.id.username_et);
        password_Et = findViewById(R.id.password_et);
        btn_login = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_bar);
        register_txt = findViewById(R.id.register_layout);

        //apply click on button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
               valadition();
            }
        });
      register_txt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(LoginActivity.this,SinupActivity.class);
              startActivity(intent);
          }
      });



    }
    private void valadition(){
       String username  = username_Et.getText().toString();
      String  password = password_Et.getText().toString();
        if(username.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .playOn(username_Et);
            username_Et.setError("Enter user name");
            username_Et.setFocusable(true);
        }else if(password.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(1)
                    .playOn(password_Et);
            password_Et.setError("Enter password");
            password_Et.setFocusable(true);

        }else {
            loginApi(username,password);

        }
    }

    private void loginApi(final String userName, final String userPassword){
        final KProgressHUD progressDialog = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.LOGIN_URL+"email="+userName+"&password="+userPassword,
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
                                 progressDialog.dismiss();
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                                TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                                tv.setText("LOGIN SUCCESSFULLY");
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
                                View layout = inflater.inflate(R.layout.wrong_layout, (ViewGroup) findViewById(R.id.custom_toast_layouttt));
                                TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                                ImageView imageView =(ImageView)layout.findViewById(R.id.imageview);
                                imageView.setImageResource(R.drawable.ic_round_person_add_disabled_24);
                                tv.setText("NOT REGSITER");
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
                            View layout = inflater.inflate(R.layout.wrong_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                            tv.setText("NOT REGSITER");
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
}
