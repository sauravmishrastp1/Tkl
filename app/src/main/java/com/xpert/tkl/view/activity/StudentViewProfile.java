package com.xpert.tkl.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.squareup.picasso.Picasso;
import com.xpert.tkl.R;

public class StudentViewProfile extends AppCompatActivity {
    private TextView nametxt,addresstxt,phonenotxt,subjecttxt,classstxt,distxt;
    Bundle bundle;
    private String name,address,phone,subject,user_id,classs,img_url,dis;
    private ImageView profileimage,backpress_;
    private Button call_now_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_profile);
        nametxt = findViewById(R.id.name_txt);
        addresstxt = findViewById(R.id.address_txt);
        phonenotxt = findViewById(R.id.phone_txt);
        subjecttxt = findViewById(R.id.maths);
        classstxt = findViewById(R.id.class_txt);
        profileimage = findViewById(R.id.profile_imag);
        call_now_btn = findViewById(R.id.buttonedit);
        distxt = findViewById(R.id.dis_txt);
        backpress_ = findViewById(R.id.backbtn);
//
//        bundle = getIntent().getExtras();
//        if(bundle!=null){
//           name = bundle.getString("name");
//           address = bundle.getString("address");
//           phone = bundle.getString("phone");
//           subject = bundle.getString("sunject");
//           user_id = bundle.getString("id");
//           classs = bundle.getString("class");
//           img_url = bundle.getString("img");
//           dis = bundle.getString("dis");
//           distxt.setText(dis);
//
//            Picasso.with(getApplicationContext()).load(img_url).into(profileimage);
//           nametxt.setText(name);
//           addresstxt.setText(address);
//           phonenotxt.setText(phone);
//           subjecttxt.setText(subject);
//           classstxt.setText("Class-"+classs);
   //     }

        call_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallBtnClick();
            }
        });

        backpress_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(StudentViewProfile.this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(getApplicationContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phone));
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
