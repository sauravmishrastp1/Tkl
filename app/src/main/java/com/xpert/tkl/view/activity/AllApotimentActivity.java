package com.xpert.tkl.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xpert.tkl.R;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.model.AppointmentModel;
import com.xpert.tkl.model.StudentData;
import com.xpert.tkl.view.adapter.Apotiment_Adapter;
import com.xpert.tkl.view.adapter.StudentDataAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllApotimentActivity extends AppCompatActivity {
    private ImageView add_apotiment;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView backpress;
    private ImageView add_app;
    private ArrayList<StudentData>appointmentModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apotiment);
        add_apotiment = findViewById(R.id.img);
        recyclerView = findViewById(R.id.recyclerview);
        add_app = findViewById(R.id.img);
        backpress = findViewById(R.id.backbtn);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllApotimentActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        progressBar = findViewById(R.id.progressbar);
        add_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllApotimentActivity.this,Add_Apotiment.class);
                startActivity(intent);
            }
        });

        stdudentapi();


    }
    private void stdudentapi(){
        appointmentModelArrayList.add(new StudentData("","Tushan","+91-95655787","","","","",R.drawable.img11));
        appointmentModelArrayList.add(new StudentData("","Goutam","+91-95655787","","","","",R.drawable.img22));
        appointmentModelArrayList.add(new StudentData("","Saurav","+91-95655787","","","","",R.drawable.img33));
        appointmentModelArrayList.add(new StudentData("","Tushan","+91-95655787","","","","",R.drawable.img11));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        Apotiment_Adapter gridProductAdapter = new Apotiment_Adapter(appointmentModelArrayList, getApplicationContext());
        recyclerView.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);


    }


    private void showStudentdata(){
        progressBar.setVisibility(View.VISIBLE);
        appointmentModelArrayList=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BaseUrl.SHOW_STUDENT,

                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);
                            String status = object.getString("status");


                            JSONArray bannerarray = object.getJSONArray("Student");




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

                                 //   appointmentModelArrayList.add(new StudentData(id,name,address,phone,class_,subject,dis,"https://xpertwebtech.in/tkl/upload/"+image));


                                }
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                Apotiment_Adapter gridProductAdapter = new Apotiment_Adapter(appointmentModelArrayList, getApplicationContext());
                                recyclerView.setAdapter(gridProductAdapter);
                                gridProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);



                            } else {

                                Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  Toast.makeText(getContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        int errorCode = 0;
                        if (error instanceof TimeoutError) {
                            // Toast.makeText(getActivity(), "Timeout Try Again", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            errorCode = -7;
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "No Connection Try Again", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            errorCode = -1;
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailure Error Try Again", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            errorCode = -6;
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Error Try Again", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            errorCode = 0;
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network error Try Again", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            errorCode = -1;
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Server rror Try Again", Toast.LENGTH_SHORT).show();

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
