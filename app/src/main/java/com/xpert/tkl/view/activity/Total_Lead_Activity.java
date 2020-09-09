package com.xpert.tkl.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xpert.tkl.R;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.model.StudentData;
import com.xpert.tkl.view.adapter.Apotiment_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Total_Lead_Activity extends AppCompatActivity {
    private ImageView add_apotiment;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView backpress;
    private ImageView add_app;
    private TextView null_data;
    private ArrayList<StudentData>appointmentModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total__lead_);
        add_apotiment = findViewById(R.id.img);
        null_data = findViewById(R.id.nolead_layout);
        recyclerView = findViewById(R.id.recyclerview);
        add_app = findViewById(R.id.img);
        backpress = findViewById(R.id.backbtn);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Total_Lead_Activity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        progressBar = findViewById(R.id.progressbar);
        add_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Total_Lead_Activity.this,Add_Apotiment.class);
                startActivity(intent);
            }
        });

        showStudentdata();


    }
//    private void stdudentapi(){
//        appointmentModelArrayList.add(new StudentData("","Tushan","+91-95655787","","","","",R.drawable .img11));
//        appointmentModelArrayList.add(new StudentData("","Goutam","+91-95655787","","","","",R.drawable.img22));
//        appointmentModelArrayList.add(new StudentData("","Saurav","+91-95655787","","","","",R.drawable.img33));
//        appointmentModelArrayList.add(new StudentData("","Tushan","+91-95655787","","","","",R.drawable.img11));
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        Apotiment_Adapter gridProductAdapter = new Apotiment_Adapter(appointmentModelArrayList, getApplicationContext());
//        recyclerView.setAdapter(gridProductAdapter);
//        gridProductAdapter.notifyDataSetChanged();
//        progressBar.setVisibility(View.GONE);
//
//
//    }


    private void showStudentdata(){
        progressBar.setVisibility(View.VISIBLE);
        appointmentModelArrayList=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url  ="https://tklpvtltd.com/tkl/api/total-lead?user_id="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);
                            String status = object.getString("status");


                            JSONArray bannerarray = object.getJSONArray("total_lead");




                            if (status.equals("200")) {



                                for (int i=0;i<bannerarray.length();i++)
                                {

                                    JSONObject jsonObject = bannerarray.getJSONObject(i);
                                    String image=jsonObject.getString("image");
                                    String id=jsonObject.getString("id");
                                    String name = jsonObject.getString("name");
                                    String class_ = jsonObject.getString("class");
                                    String dis = jsonObject.getString("description");
                                    String phone = jsonObject.getString("phone_no");
                                    String subject = jsonObject.getString("subject");
                                    String address = jsonObject.getString("address");
                                    String view_status = jsonObject.getString("status");
                                    appointmentModelArrayList.add(new StudentData(id,name,address,phone,class_,subject,dis,BaseUrl.IMAGE_URL+image," ",status));


                                }
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                Apotiment_Adapter gridProductAdapter = new Apotiment_Adapter(appointmentModelArrayList, getApplicationContext());
                                recyclerView.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                null_data.setVisibility(View.GONE);



                            } else {

                                Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                null_data.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            null_data.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        int errorCode = 0;
                        if (error instanceof TimeoutError) {
                            // Toast.makeText(getActivity(), "Timeout Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = -7;
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No Connection Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = -1;
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailure Error Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = -6;
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = 0;
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network error Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = -1;
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Server rror Try Again", Toast.LENGTH_SHORT).show();
                            null_data.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorCode = -8;
                        }
                    }
                }) {

            @Override
            public void deliverError(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Cache.Entry entry = this.getCacheEntry();
                    if(entry != null) {
                        Response<String> response = parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        deliverResponse(response.result);
                        return;
                    }
                }
                super.deliverError(error);
            }

        };

        queue.getCache().clear();
        queue.getCache().remove(BaseUrl.SLIDER_URL);
        queue.add(stringRequest);
    }
}
