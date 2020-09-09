package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.xpert.tkl.model.MyWalletModel;
import com.xpert.tkl.model.Subsription_plane_model;
import com.xpert.tkl.view.adapter.MyWalletLayout;
import com.xpert.tkl.view.adapter.Subscription_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Suscription_Plane_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Subsription_plane_model>subsription_plane_models = new ArrayList<>();
    private ProgressBar progressBar;
    private ImageView backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription__plane_);
        progressBar = findViewById(R.id.visbilitygone);
        recyclerView = findViewById(R.id.recyclerview_);
        backpress = findViewById(R.id.backbtn);
        get_subsrition_plane();
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void get_subsrition_plane()
       {
           final KProgressHUD progressDialog = KProgressHUD.create(Suscription_Plane_Activity.this)
                   .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                   .setLabel("Please wait")
                   .setMaxProgress(100)
                   .show();
           progressDialog.setProgress(90);
        subsription_plane_models.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://tklpvtltd.com/tkl/api/subscription-plan";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray moneyarray = jsonObject.getJSONArray("subscription-plan");
                    if(status.equals("200")){
                        for (int i=0;i<moneyarray.length();i++) {


                            JSONObject jsonObject1 = moneyarray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String price = jsonObject1.getString("price");
                            String type = jsonObject1.getString("type");
                            String total_price = jsonObject1.getString("total_price");
                            String active = jsonObject1.getString("active");
                            String dis = jsonObject1.getString("description");
                            subsription_plane_models.add(new Subsription_plane_model(id,title,price,type,total_price,active,dis));


                            LinearLayoutManager gridLayoutManager1 = new LinearLayoutManager(Suscription_Plane_Activity.this);

                            recyclerView.setLayoutManager(gridLayoutManager1);
                            Subscription_Adapter gridProductAdapter = new Subscription_Adapter(subsription_plane_models, Suscription_Plane_Activity.this);
                            recyclerView.setAdapter(gridProductAdapter);
                            gridProductAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                    }
                }catch (Exception e){
                    Toast.makeText( Suscription_Plane_Activity.this, "Somthing went wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Suscription_Plane_Activity.this, "server error!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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