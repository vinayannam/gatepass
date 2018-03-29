package com.example.vinayasd.gatepass;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.vinayasd.gatepass.modal.Form;
import com.example.vinayasd.gatepass.sql.DatabaseAccess;

import java.text.Normalizer;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    TextView txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String userName,sRequest="1",pRequest="0",wRequest="0";
    private EditText name,home,room,reason,phone;
    private TextView fromDate,fromTime,toDate,toTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        userName = getIntent().getStringExtra("USERNAME");
    }
    public void date(View v){
        txtDate = (TextView) v;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void time(View v){
        txtTime =(TextView) v;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    public void parentRequest(View v){


        Form pass = new Form();

        name = (EditText)findViewById(R.id.form_name);
        home = (EditText)findViewById(R.id.form_home);
        room = (EditText)findViewById(R.id.form_room);
        fromDate = (TextView)findViewById(R.id.form_fromDate);
        fromTime = (TextView)findViewById(R.id.form_fromTime);
        toDate = (TextView)findViewById(R.id.form_toDate);
        toTime = (TextView)findViewById(R.id.form_toTime);
        reason = (EditText)findViewById(R.id.form_reason);
        phone = (EditText)findViewById(R.id.form_phone);

        String s1 = name.getText().toString().trim(),
        s2 = home.getText().toString().trim(),
        s3 = room.getText().toString().trim(),
        s4 = fromDate.getText().toString().trim(),
        s5 = fromTime.getText().toString().trim(),
        s6 = toDate.getText().toString().trim(),
        s7 = toTime.getText().toString().trim(),
        s8 = reason.getText().toString().trim(),
        s9 = phone.getText().toString().trim();

        if(!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals("") && !s6.equals("") && !s7.equals("") && !s8.equals("") && !s9.equals("")) {
            pass.setUsername(userName.trim());
            pass.setName(name.getText().toString().trim());
            pass.setHome(home.getText().toString().trim());
            pass.setRoom(room.getText().toString().trim());
            pass.setFromDate(fromDate.getText().toString().trim());
            pass.setFromTime(fromTime.getText().toString().trim());
            pass.setTodate(toDate.getText().toString().trim());
            pass.setToTime(toTime.getText().toString().trim());
            pass.setReason(reason.getText().toString().trim());
            pass.setPhone(phone.getText().toString().trim());
            pass.setsRequest(sRequest.trim());
            pass.setpRequest(pRequest.trim());
            pass.setwRequest(wRequest.trim());


            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

            databaseAccess.addPassRequest(pass);

            Intent intent = new Intent(FormActivity.this, Main2Activity.class);
            intent.putExtra("USERNAME", pass.getUsername().toString().trim());
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Don't be smart", Toast.LENGTH_SHORT).show();
        }
    }
}
