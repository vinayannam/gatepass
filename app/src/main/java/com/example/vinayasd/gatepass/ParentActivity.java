package com.example.vinayasd.gatepass;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.vinayasd.gatepass.adapters.ParentAdapter;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

public class ParentActivity extends AppCompatActivity {
    private TextView textViewName;

    private RecyclerView recyclerView;

    private ParentAdapter parentAdapter;

    private List<Form> passes;
    String un;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        initViews();
        final String emailFromIntent = getIntent().getStringExtra("USERNAME");
        textViewName.setText(emailFromIntent);

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        un = databaseAccess.findStudentOfParent(emailFromIntent.trim());

        passes = databaseAccess.getAllMyPasses(un.trim());

        parentAdapter = new ParentAdapter(passes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(parentAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerParent);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ParentActivity.this);

                un = databaseAccess.findStudentOfParent(emailFromIntent.trim());

                passes = databaseAccess.getAllMyPasses(un.trim());

                parentAdapter = new ParentAdapter(passes);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(parentAdapter);

                swipeContainer.setRefreshing(false);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }
    private void initViews() {


        textViewName = (TextView) findViewById(R.id.parentText);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_parent);

    }
    public void logoutParent(View v){
        Intent intent = new Intent(ParentActivity.this, MainActivity.class);
        intent.putExtra("USERNAME", textViewName.getText().toString().trim());
        startActivity(intent);
    }



}

