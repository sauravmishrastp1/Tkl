package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.xpert.tkl.model.MyWalletModel;
import com.xpert.tkl.view.adapter.MyWalletLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyWallet_Activity extends AppCompatActivity {
     ImageView bacbress;
     private View add_wallet;
     private TextView walletmoney,nametxt,phonetxt;
    private ArrayList<MyWalletModel> myWalletModels=new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView validity;
    private TextView active_;
    private Button buy_plane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_);
        bacbress = findViewById(R.id.backbtn);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerview_);
        active_ = findViewById(R.id.active);
        walletmoney = findViewById(R.id.wallet_money);
        nametxt = findViewById(R.id.name_txt);
        validity=findViewById(R.id.validaty_);
        phonetxt = findViewById(R.id.email_txt);
        buy_plane = findViewById(R.id.add_money_wallet_);
         String name = SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
         String phone = SharedPrefManager.getInstance(getApplicationContext()).getUser().getPhone();
         phonetxt.setText(phone);
       //  statedata();
        getwalletblance1();
         nametxt.setText(name);
        add_wallet = findViewById(R.id.add_money_wallet);
        bacbress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        add_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Wallter_Layout.class);
                startActivity(intent);
            }
        });
        buy_plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Suscription_Plane_Activity.class);
                startActivity(intent);
            }
        });
        getwalletblance();
        //add_money();
        //Toast.makeText(this, ""+SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), Toast.LENGTH_SHORT).show();
    }
    private void getwalletblance1(){
        final KProgressHUD progressDialog = KProgressHUD.create(MyWallet_Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        // progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/view-wallet?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("$data");
                    if(status.equals("200")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject walletjson = jsonArray.getJSONObject(i);
                           String wallet_momey = walletjson.getString("money");

                            walletmoney.setText("₹"+wallet_momey);
                            progressDialog.dismiss();
                        }



                    }else  {

                        progressDialog.dismiss();

                        //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){

                    progressDialog.dismiss();
                    //Toast.makeText(MyWallet_Activity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(MyWallet_Activity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
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
    private void getwalletblance(){
        final KProgressHUD progressDialog = KProgressHUD.create(MyWallet_Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();
        progressDialog.setProgress(90);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/active-user-plan?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("Active-User-Plan");
                    if(status.equals("200")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject walletjson = jsonArray.getJSONObject(i);
                            String title = walletjson.getString("title");
                            String price =walletjson.getString("price");
                           // String gst = walletjson.getString("gst%");
                            String  total_price = walletjson.getString("total_price");
                            String type = walletjson.getString("type");
                        //    walletmoney.setText(title);
                            validity.setText(title);
                            active_.setText("Active");
                           // Toast.makeText(MyWallet_Activity.this, ""+title, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }




                    }else {
                       // nul_layout.setVisibility(View.VISIBLE);
                       // not_nul_layout.setVisibility(View.GONE);
                        progressDialog.dismiss();
                      //  Toast.makeText(MyWallet_Activity.this, "Data is not submit", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){

                    progressDialog.dismiss();
                    //Toast.makeText(MyWallet_Activity.this, "somtihing wet wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(MyWallet_Activity.this, "Server Error!!", Toast.LENGTH_SHORT).show();
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


    private void add_money(){
        myWalletModels.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/active-wallet";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              try{
                  JSONObject jsonObject = new JSONObject(response);
                  String status = jsonObject.getString("status");
                  JSONArray moneyarray = jsonObject.getJSONArray("Active-wallet");
                  if(status.equals("200")){
                      for (int i=0;i<moneyarray.length();i++) {
                          JSONObject jsonObject1 = moneyarray.getJSONObject(i);
                          String id = jsonObject1.getString("id");
                          String amount = jsonObject1.getString("amount");
                          myWalletModels.add(new MyWalletModel(amount,"Rs.25 Cashback"));


                          GridLayoutManager gridLayoutManager1 = new GridLayoutManager(MyWallet_Activity.this, 2);

                          recyclerView.setLayoutManager(gridLayoutManager1);
                          MyWalletLayout gridProductAdapter = new MyWalletLayout(myWalletModels, MyWallet_Activity.this);
                          recyclerView.setAdapter(gridProductAdapter);
                          gridProductAdapter.notifyDataSetChanged();
                      }

                  }
            }catch (Exception e){
                  Toast.makeText(MyWallet_Activity.this, "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyWallet_Activity.this, "server error!!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.getCache().clear();

        VolleySingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    }
