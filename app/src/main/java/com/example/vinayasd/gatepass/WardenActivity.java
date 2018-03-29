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

import com.example.vinayasd.gatepass.adapters.ParentAdapter;
import com.example.vinayasd.gatepass.adapters.WardenAdapter;
import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.util.List;

public class WardenActivity extends AppCompatActivity {

    private TextView textViewName;

    private RecyclerView recyclerView;


    private WardenAdapter wardenAdapter;

    private List<Form> passes;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden);
        initViews();
        String emailFromIntent = getIntent().getStringExtra("USERNAME");
        textViewName.setText(emailFromIntent);
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);


        passes = databaseAccess.getAllPassesForWarden();

        wardenAdapter = new WardenAdapter(passes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(wardenAdapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerWarden);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(WardenActivity.this);


                passes = databaseAccess.getAllPassesForWarden();

                wardenAdapter = new WardenAdapter(passes);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(wardenAdapter);
                swipeContainer.setRefreshing(false);

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }
    private void initViews() {

        textViewName = (TextView) findViewById(R.id.wardenText);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_warden);

    }
    public void logoutWarden(View v){
        Intent intent = new Intent(WardenActivity.this, MainActivity.class);
        intent.putExtra("USERNAME", textViewName.getText().toString().trim());
        startActivity(intent);
    }
}
