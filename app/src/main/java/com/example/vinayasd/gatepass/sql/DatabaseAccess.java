package com.example.vinayasd.gatepass.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vinayasd.gatepass.modal.DateModal;
import com.example.vinayasd.gatepass.modal.Form;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vinayasd on 06/04/17.
 */

public class DatabaseAccess {



    private static final String[] TABLE_USER = {"students","parents","warden"};


    private static final String TABLE_FORM = "passes";


    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_FORM_NAME = "name";
    private static final String COLUMN_FORM_HOME = "home";
    private static final String COLUMN_FORM_ROOM = "room";
    private static final String COLUMN_FORM_FROMDATE = "from_date";
    private static final String COLUMN_FORM_FROMTIME = "from_time";
    private static final String COLUMN_FORM_TODATE = "to_date";
    private static final String COLUMN_FORM_TOTIME = "to_time";
    private static final String COLUMN_FORM_REASON = "reason";
    private static final String COLUMN_FORM_PHONE = "phone";
    private static final String COLUMN_FORM_SREQUEST = "s_request";
    private static final String COLUMN_FORM_PREQUEST = "p_request";
    private static final String COLUMN_FORM_WREQUEST = "w_request";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private boolean flagParent = false;
    private boolean flagWarden = false;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }



    public void addPassRequest(Form pass) {
        this.open();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FORM_NAME, pass.getName());
        values.put(COLUMN_FORM_PHONE, pass.getPhone());
        values.put(COLUMN_FORM_FROMDATE, pass.getFromDate());
        values.put(COLUMN_FORM_FROMTIME, pass.getFromTime());
        values.put(COLUMN_FORM_HOME, pass.getHome());
        values.put(COLUMN_FORM_ROOM, pass.getRoom());
        values.put(COLUMN_FORM_PREQUEST, pass.getpRequest());
        values.put(COLUMN_FORM_REASON, pass.getReason());
        values.put(COLUMN_FORM_TODATE, pass.getTodate());
        values.put(COLUMN_FORM_TOTIME, pass.getToTime());
        values.put(COLUMN_FORM_SREQUEST, pass.getsRequest());
        values.put(COLUMN_FORM_WREQUEST, pass.getwRequest());
        values.put(COLUMN_USERNAME, pass.getUsername());



        this.database.insert(TABLE_FORM, null, values);
        this.database.close();
    }


    public List<Form> getAllMyPasses(String username){
        List<Form> passsesList = new ArrayList<Form>();

        String selectQuery = "SELECT  * FROM " + TABLE_FORM +" WHERE username = ?";



        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));

        this.open();
        Cursor cursor = this.database.rawQuery(selectQuery, new String[]{username.trim()});


        if (cursor.moveToFirst()) {
            do {
                Form pass = new Form();
                pass.setID(Integer.parseInt(cursor.getString(0)));
                pass.setUsername(cursor.getString(1));
                pass.setName(cursor.getString(2));
                pass.setHome(cursor.getString(3));
                pass.setRoom(cursor.getString(4));
                pass.setFromDate(cursor.getString(5));
                pass.setFromTime(cursor.getString(6));
                pass.setTodate(cursor.getString(7));
                pass.setToTime(cursor.getString(8));
                pass.setReason(cursor.getString(9));
                pass.setPhone(cursor.getString(10));
                pass.setsRequest(cursor.getString(11));
                pass.setpRequest(cursor.getString(12));
                pass.setwRequest(cursor.getString(13));



                if(this.timeCheck(timeStamp,pass.getTodate()+" "+pass.getToTime())){
                    if(flagParent) {
                        if(!cursor.getString(12).equals("1")){
                            passsesList.add(pass);
                            Log.d("update", cursor.getString(11) + " " + cursor.getString(12) + " " + cursor.getString(13));
                        }
                    }else {
                        passsesList.add(pass);
                        Log.d("update", cursor.getString(11) + " " + cursor.getString(12) + " " + cursor.getString(13));
                    }
                }

            } while (cursor.moveToNext());
        }
        this.close();

        return passsesList;
    }

    public List<Form> getAllPassesForWarden(){
        List<Form> passsesList = new ArrayList<Form>();

        String selectQuery = "SELECT  * FROM " + TABLE_FORM +" WHERE "+COLUMN_FORM_PREQUEST+"= ?";



        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));

        this.open();
        Cursor cursor = this.database.rawQuery(selectQuery, new String[]{"1"});


        if (cursor.moveToFirst()) {
            do {
                Form pass = new Form();
                pass.setID(Integer.parseInt(cursor.getString(0)));
                pass.setUsername(cursor.getString(1));
                pass.setName(cursor.getString(2));
                pass.setHome(cursor.getString(3));
                pass.setRoom(cursor.getString(4));
                pass.setFromDate(cursor.getString(5));
                pass.setFromTime(cursor.getString(6));
                pass.setTodate(cursor.getString(7));
                pass.setToTime(cursor.getString(8));
                pass.setReason(cursor.getString(9));
                pass.setPhone(cursor.getString(10));
                pass.setsRequest(cursor.getString(11));
                pass.setpRequest(cursor.getString(12));
                pass.setwRequest(cursor.getString(13));



                if(this.timeCheck(timeStamp,pass.getTodate()+" "+pass.getToTime())) {

                    if (cursor.getString(12).equals("1") && cursor.getString(13).equals("0")) {
                        passsesList.add(pass);
                        Log.d("update", cursor.getString(11) + " " + cursor.getString(12) + " " + cursor.getString(13));
                    }
                }
            } while (cursor.moveToNext());
        }
        this.close();

        return passsesList;
    }

    public boolean checkStudent(String email, String password) {


        String[] columns = {
                COLUMN_USER_ID
        };

        this.open();

        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";


        String[] selectionArgs = {email, password};



            Cursor cursor = this.database.query(TABLE_USER[0],
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            int cursorCount = cursor.getCount();

            cursor.close();
            if (cursorCount > 0) {
                flagWarden = false;
                flagParent = false;
                return true;
            }

        this.close();
        flagWarden = false;
        flagParent = false;
        return false;

    }

    public boolean checkParent(String email, String password) {


        String[] columns = {
                COLUMN_USER_ID
        };

        this.open();

        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";


        String[] selectionArgs = {email, password};



        Cursor cursor = this.database.query(TABLE_USER[1],
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        if (cursorCount > 0) {
            flagWarden = false;
            flagParent = true;
            return true;
        }

        this.close();
        flagWarden = false;
        flagParent = false;
        return false;

    }

    public boolean checkWarden(String email, String password) {


        String[] columns = {
                COLUMN_USER_ID
        };

        this.open();

        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";


        String[] selectionArgs = {email, password};



        Cursor cursor = this.database.query(TABLE_USER[2],
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        if (cursorCount > 0) {
            flagParent = false;
            flagWarden = true;
            return true;
        }

        this.close();
        flagParent = false;
        flagWarden = false;
        return false;

    }

    public boolean timeCheck(String s1, String s2){
        DateModal d1 = new DateModal(s2);
        DateModal d2 = new DateModal(s1);

        if(d2.year<d1.year) {
            return true;
        }else if(d2.year==d1.year) {
            if(d2.month<d1.month) {
                return true;
            }else if(d2.month==d1.month) {
                if(d2.date<d1.date) {
                    return true;
                }else if(d2.date==d1.date) {

                    if(d2.hours<d1.hours){
                        return true;
                    }
                    else if(d2.hours==d1.hours){
                        if(d2.min<=d1.min){
                            return true;
                        }else{
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String findStudentOfParent(String un){

        String ans = new String();

        String selectQuery = "SELECT  username FROM " + TABLE_USER[0] +" WHERE parentuser = ?";

        this.open();

        Cursor cursor = this.database.rawQuery(selectQuery, new String[]{un.trim()});

        if (cursor.moveToFirst()) {
            do {
                ans = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        this.close();

        return ans;
    }

    public void parentAccept(int position, List<Form> passes) {
        int s = passes.get(position).getID();

        Log.d("accept", ""+s);
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FORM_PREQUEST, "1");
        this.database.update(TABLE_FORM, cv, COLUMN_USER_ID + "= ?", new String[] {String.valueOf(s)});

        this.close();
    }

    public void parentDecline(int position, List<Form> passes) {
        int s = passes.get(position).getID();
        Log.d("decline", ""+s);
        this.open();
        this.database.delete(TABLE_FORM, COLUMN_USER_ID + " = ?",
                new String[] {String.valueOf(s)});
        this.close();
    }

    public void wardenAccept(int position, List<Form> passes) {
        int s = passes.get(position).getID();

        Log.d("accept", ""+s);
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FORM_WREQUEST, "1");
        this.database.update(TABLE_FORM, cv, COLUMN_USER_ID + "= ?", new String[] {String.valueOf(s)});

        this.close();
    }

    public void wardenDecline(int position, List<Form> passes) {
        int s = passes.get(position).getID();
        Log.d("decline", ""+s);
        this.open();
        this.database.delete(TABLE_FORM, COLUMN_USER_ID + " = ?",
                new String[] {String.valueOf(s)});
        this.close();
    }

    public int colorState(int position, List<Form> passes){
        String selectQuery = "SELECT  * FROM " + TABLE_FORM +" WHERE _id = ?";

        int s,p,w;

        int cs=0;


        this.open();
        Cursor cursor = this.database.rawQuery(selectQuery, new String[]{String.valueOf(passes.get(position).getID())});

        if (cursor.moveToFirst()) {
            do {

                p = Integer.parseInt(cursor.getString(12).trim());
                w = Integer.parseInt(cursor.getString(13).trim());




                    if(p==1&&w==0){
                        cs = 1;

                    }else if(p==1&&w==1){
                        cs = 2;
                    }
                System.out.println(cs);

            } while (cursor.moveToNext());
        }
        this.close();


        return cs;
    }

}
