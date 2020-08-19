package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xpert.tkl.R;

public class Top_ReviewActivity extends AppCompatActivity {
    ImageView backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__review);
        backpress = findViewById(R.id.backbtn);
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}