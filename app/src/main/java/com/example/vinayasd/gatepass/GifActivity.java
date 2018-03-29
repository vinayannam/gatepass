package com.example.vinayasd.gatepass;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GifActivity extends AppCompatActivity {

    private TextView textViewName;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        initViews();

        final String emailFromIntent = getIntent().getStringExtra("USERNAME");
        textViewName.setText(emailFromIntent);
        img = (ImageView)findViewById(R.id.imgGif);
        Glide.with(this).load("file:///android_asset/fire.gif").into(img);

    }

    private void initViews() {


        textViewName = (TextView) findViewById(R.id.gif1);

    }
    public void logoutGif(View v){
        Intent intent = new Intent(GifActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
