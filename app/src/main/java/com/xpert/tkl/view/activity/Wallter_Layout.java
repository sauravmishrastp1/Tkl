package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.paykun.sdk.PaykunApiCall;
import com.paykun.sdk.eventbus.Events;
import com.paykun.sdk.eventbus.GlobalBus;
import com.paykun.sdk.helper.PaykunHelper;
import com.xpert.tkl.Presenter.utils.VolleySingleton;
import com.xpert.tkl.R;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.constant.SharedPrefManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Wallter_Layout extends AppCompatActivity {
     private EditText money_enter;
     private String addmoney;
    private String Testacceskey="289D665931662D236BFD9F8D156E2D8B";
    private String testmerchentid="384135165653649";
    private String TestacceskeyLIVE="6A75D3707CC25C07001C004D34286CD3";
    private String testmerchentidLIVE="837773351399967";
    private Button addmonry;
    private ImageView backpress;
    private String id;
    private Bundle bundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallter__layout);
        money_enter = findViewById(R.id.nameEt);
        addmonry = findViewById(R.id.sbumitnow);
        backpress = findViewById(R.id.backbtn);
        bundle = getIntent().getExtras();
             id = bundle.getString("id");
            addmoney = bundle.getString("money");





                    // mCurrentEnv = Instamojo.Environment.PRODUCTION;
                    //Toast.makeText(Wallter_Layout.this, "money->"+addmoney, Toast.LENGTH_SHORT).show();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("merchant_id", testmerchentid);
                        object.put("access_token",Testacceskey);
                        object.put("customer_name", SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
                        object.put("customer_email","sauravmishrastp1@gmail.com");
                        object.put("customer_phone", "9560618681");
                        object.put("product_name","Add wallet");
                        object.put("order_no",System.currentTimeMillis()); // order no. should have 10 to 30 character in numeric format
                        object.put("amount",addmoney);  // minimum amount should be 10
                        object.put("isLive",false); // need to send false if you are in sandbox mode

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Wallter_Layout.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    new PaykunApiCall.Builder(Wallter_Layout.this).sendJsonObject(object); // Paykun api to initialize your payment and send info.


                    // mCurrentEnv = Instamojo.Environment.PRODUCTION;




        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallter_Layout.this,HomeActivity.class);
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        // Register this activity to listen to event.
        GlobalBus.getBus().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unregister from activity
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResults(Events.PaymentMessage message) {
        if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
           // onpaymnetcomplete();
            update_wallet(id);
            // do your stuff here
            // message.getTransactionId() will return your failed or succeed transaction id
            /* if you want to get your transaction detail call message.getTransactionDetail()
             *  getTransactionDetail return all the field from server and you can use it here as per your need
             *  For Example you want to get Order id from detail use message.getTransactionDetail().order.orderId */

            if(!TextUtils.isEmpty(message.getTransactionId())) {
                Toast.makeText(Wallter_Layout.this, "Your Transaction is succeed with transaction id : "+message.getTransactionId(),
                        Toast.LENGTH_SHORT).show();


            }
        }

        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
            // do your stuff here
            Toast.makeText(Wallter_Layout.this,"Your Transaction is failed",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
            // do your stuff here
            Toast.makeText(Wallter_Layout.this,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.LENGTH_SHORT).show();
        }else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
            // do your stuff here
            Toast.makeText(Wallter_Layout.this,"Access Token missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_MISSING)){
            // do your stuff here
            Toast.makeText(Wallter_Layout.this,"Merchant Id is missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQUEST)){
            Toast.makeText(Wallter_Layout.this,"Invalid Request",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_NETWORK_NOT_AVAILABLE)){
            Toast.makeText(Wallter_Layout.this,"Network is not available",Toast.LENGTH_SHORT).show();
        }
    }

    private void update_wallet(String idd){
        RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/user-subscription-plan?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&subcription_id="+id+"&plan_id="+System.currentTimeMillis();
        // String url ="https://tklpvtltd.com/tkl/api/add-wallet?user_id="+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()+"&money="+addmoney;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              try {
                  JSONObject jsonObject = new JSONObject(response);
                  String status = jsonObject.getString("status");
                  if(status.equals("200")){
                      Intent intent = new Intent(getApplicationContext(),MyWallet_Activity.class);
                      startActivity(intent);
                      LayoutInflater inflater = getLayoutInflater();
                      View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_layout));
                      TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                      tv.setText("Successfully");
                      Toast toast = new Toast(getApplicationContext());
                      toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
                      toast.setDuration(Toast.LENGTH_SHORT);
                      toast.setView(layout);
                      toast.show();
                  }else {
                      Toast.makeText(Wallter_Layout.this, "Operation fail", Toast.LENGTH_SHORT).show();
                  }

              }catch (Exception e){
                  Toast.makeText(Wallter_Layout.this, "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "server error!!"+error.networkResponse, Toast.LENGTH_SHORT).show();
            }
        }){

//                @Override
//                protected Map getParams()
//                {
//                    Map params = new HashMap();
//                    params.put("user_id",SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
//                    params.put("money",addmoney);
//
//                    return params;
//                }

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
