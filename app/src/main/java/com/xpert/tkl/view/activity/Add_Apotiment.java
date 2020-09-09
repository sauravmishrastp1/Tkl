package com.xpert.tkl.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xpert.tkl.R;

public class Add_Apotiment extends AppCompatActivity {
    private ImageView bacbress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__apotiment);
        bacbress = findViewById(R.id.backbtn);
        bacbress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                
            }
        });
    }
    
}