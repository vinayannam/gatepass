package com.example.vinayasd.gatepass.modal;

/**
 * Created by vinayasd on 07/04/17.
 */

public class DateModal {
    public int year;
    public int month;
    public int date;
    public int hours;
    public int min;
    public  DateModal(String s){
        String[] a = s.split(" ");
        String[] b = a[0].split("-");
        String[] c = a[1].split(":");
        this.year = Integer.parseInt(b[2]);
        this.month = Integer.parseInt(b[1]);
        this.date = Integer.parseInt(b[0]);
        this.hours = Integer.parseInt(c[0]);
        this.min = Integer.parseInt(c[1]);
    }
}
