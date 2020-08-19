package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xpert.tkl.R;

public class MyWallet_Activity extends AppCompatActivity {
     ImageView bacbress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_);
        bacbress = findViewById(R.id.backbtn);
        bacbress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}