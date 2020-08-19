package com.xpert.tkl.view.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.xpert.tkl.R;
import com.xpert.tkl.constant.LocationAssets;
import com.xpert.tkl.view.adapter.Apotiment_Adapter;
import com.xpert.tkl.view.fragment.SideMenuFragment;
import com.xpert.tkl.api.BaseUrl;
import com.xpert.tkl.constant.SharedPrefManager;
import com.xpert.tkl.model.BannerSliderModel;
import com.xpert.tkl.model.StudentData;
import com.xpert.tkl.view.adapter.BannerSliderAdapter;
import com.xpert.tkl.view.adapter.StudentDataAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private Location mylocation;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    Geocoder geocoder;
    List<Address> addresses;
    public static String address;
    private SliderView sliderView;
    private RecyclerView recyclerView;
    private ArrayList<BannerSliderModel>bannerSliderModelList= new ArrayList<>();
    private ArrayList<StudentData>appointmentModelArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private SlidingMenu menu;
    private ImageView sidemenu;
    private View wallet;
    private TextView loaction_txt;
    private  View reviewlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sliderView = findViewById(R.id.imageSlider);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);
        loaction_txt = findViewById(R.id.text_location);
        reviewlayout = findViewById(R.id.reviewlayout);
        sidemenu = findViewById(R.id.backbtn);
        wallet  = findViewById(R.id.carlayout);

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyWallet_Activity.class);
                startActivity(intent);
                String h ="nkgn";
            }
        });
        reviewlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Top_ReviewActivity.class);
                startActivity(intent);
            }
        });
        geocoder = new Geocoder(this, Locale.getDefault());
        setUpGClient();
       // getMyLocation();


        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
      sliderApi();
        stdudentapi();

        setSideBar();

        SideMenuFragment sideMenuFragment = new SideMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.side_menu_container, sideMenuFragment, "SideMenuFragment")
                .commit();

        sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSideMenu();
            }
        });
    }
    public void onSideMenu() {
        menu.toggle();
    }

    private void setSideBar() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setFadeDegree(0.75f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.ly_frame_layout);
    }


    private void sliderApi(){

        progressBar.setVisibility(View.VISIBLE);
        bannerSliderModelList=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://xpertwebtech.in/tkl/api/slider";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object=new JSONObject(response);
                            String status = object.getString("status");


                            JSONArray bannerarray = object.getJSONArray("slider");




                            if (status.equals("200")) {



                                for (int i=0;i<bannerarray.length();i++)
                                {

                                    JSONObject jsonObject = bannerarray.getJSONObject(i);
                                    String bannerimage=jsonObject.getString("image");
                                    String id=jsonObject.getString("id");


                                    bannerSliderModelList.add(new BannerSliderModel("https://xpertwebtech.in/tkl/upload/"+bannerimage,id));


                                }

                                BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(bannerSliderModelList, getApplicationContext());
                                sliderView.setSliderAdapter(bannerSliderAdapter);
                                sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                                sliderView.setIndicatorSelectedColor(Color.parseColor("#00264d"));
                                sliderView.setIndicatorUnselectedColor(Color.WHITE);
                                sliderView.setScrollTimeInSec(2); //set scroll delay in seconds :
                                sliderView.startAutoCycle();
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
    private void stdudentapi(){
        appointmentModelArrayList.add(new StudentData("","Tushan","Greatre noida","","9th","Matths","",R.drawable.img11));
        appointmentModelArrayList.add(new StudentData("","Goutam","noida","","10th","Science","Science",R.drawable.img22));
        appointmentModelArrayList.add(new StudentData("","Saurav","delhi","","12th","Chemistry","Chemistry",R.drawable.img33));
        appointmentModelArrayList.add(new StudentData("","Tushan","noida","","BSCE","Physic","Physic",R.drawable.img11));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        StudentDataAdapter gridProductAdapter = new StudentDataAdapter(appointmentModelArrayList, getApplicationContext());
        recyclerView.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();



    }


    private void showStudentdata(){
        progressBar.setVisibility(View.VISIBLE);
        bannerSliderModelList=new ArrayList<>();
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


                                }



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

private void scaleView(View view){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -50, 100);
        translateAnimation.setDuration(600);
        translateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        translateAnimation.setRepeatMode(ValueAnimator.REVERSE);
        view.setAnimation(translateAnimation);
        }

private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, 0, this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
        googleApiClient.connect();
        }

@Override
public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
        double lat = mylocation.getLatitude();
        double lon = mylocation.getLongitude();
        LocationAssets.latitude = mylocation.getLatitude();
        LocationAssets.longitude = mylocation.getLongitude();
        //Or Do whatever you want with your location
        try {
        addresses = geocoder.getFromLocation(lat, lon, 1);
        address = addresses.get(0).getLocality();
        loaction_txt.setText(address);
        // Constants.currentAddress = address;
       // Toast.makeText(this, "Your Location is locality: "+address, Toast.LENGTH_LONG).show();
        // startActivity(new Intent(LocationActivity.this, HomeActivity.class));
        // LocationActivity.this.finish();
        } catch (IOException e) {
        e.printStackTrace();
       // Toast.makeText(this, "Location Not Found !", Toast.LENGTH_SHORT).show();
        }
        }
        }

@Override
public void onConnected(Bundle bundle) {
        checkPermissions();
        }

@Override
public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
        }

@Override
public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
        }

public void getMyLocation() {
        if (googleApiClient != null) {
        if (googleApiClient.isConnected()) {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
        Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
        mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
@SuppressLint("RestrictedApi")
LocationRequest locationRequest = new LocationRequest();
                            locationRequest.setInterval(3000);
                            locationRequest.setFastestInterval(3000);
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                            builder.setAlwaysShow(true);
                            LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                            PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi
        .checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

     @Override
 public void onResult(LocationSettingsResult result) {
final Status status = result.getStatus();
        switch (status.getStatusCode()) {
        case LocationSettingsStatusCodes.SUCCESS:
        // All location settings are satisfied.
        // You can initialize location requests here.
        int permissionLocation = ContextCompat
        .checkSelfPermission(HomeActivity.this,
        Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
        mylocation = LocationServices.FusedLocationApi
        .getLastLocation(googleApiClient);
        }
        break;
        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
        // Location settings are not satisfied.
        // But could be fixed by showing the user a dialog.
        try {
        // Show the dialog by calling startResolutionForResult(),
        // and check the result in onActivityResult().
        // Ask to turn on GPS automatically
        status.startResolutionForResult(HomeActivity.this,
        REQUEST_CHECK_SETTINGS_GPS);
        } catch (IntentSender.SendIntentException e) {
        // Ignore the error.
        }
        break;
        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
        // Location settings are not satisfied.
        // However, we have no way
        // to fix the
        // settings so we won't show the dialog.
        // finish();
        break;
        }
        }
        });
        }
        }
        }
        }


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case REQUEST_CHECK_SETTINGS_GPS:
        switch (resultCode) {
        case Activity.RESULT_OK:
        getMyLocation();
        break;
        case Activity.RESULT_CANCELED:
        Toast.makeText(this, "Please Switch ON GPS for using this application", Toast.LENGTH_SHORT).show();
        break;
        }
        break;
        }
        }

private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
        Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (!listPermissionsNeeded.isEmpty()) {
        ActivityCompat.requestPermissions(this,
        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
        } else {
        getMyLocation();
        }

        }

@Override
public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
        Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
        getMyLocation();
        }
        }

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
@Override
public void onBackPressed() {
        this.finishAffinity();
        }

private void getCityList()
        {

        }
        }


