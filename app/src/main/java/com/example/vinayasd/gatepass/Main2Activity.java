package com.example.vinayasd.gatepass;

import android.content.Intent;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.TextView;
import com.example.vinayasd.gatepass.adapters.PassAdapter;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TextView textViewName;
    private TextView newRequest;


    private RecyclerView recyclerView;

    private PassAdapter passesAdapter;

    private List<Form>  passes;

    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();




        final String emailFromIntent = getIntent().getStringExtra("USERNAME");
        textViewName.setText(emailFromIntent);
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        passes = databaseAccess.getAllMyPasses(emailFromIntent.trim());

        passesAdapter = new PassAdapter(passes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(passesAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerMain2);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(Main2Activity.this);

                passes = databaseAccess.getAllMyPasses(emailFromIntent.trim());

                passesAdapter = new PassAdapter(passes);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(passesAdapter);
                swipeContainer.setRefreshing(false);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    private void initViews() {


        textViewName = (TextView) findViewById(R.id.main2);
        newRequest = (TextView) findViewById(R.id.main3);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

    }

    public void newForm(View v){
        Intent intent = new Intent(Main2Activity.this, FormActivity.class);
        intent.putExtra("USERNAME", textViewName.getText().toString().trim());
        startActivity(intent);
    }

    public void logoutStudent(View v){
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putExtra("USERNAME", textViewName.getText().toString().trim());
        startActivity(intent);
    }
}
