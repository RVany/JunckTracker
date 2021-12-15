package com.appsky.junktracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackerActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";
    private ArrayList<String> bitDates = new ArrayList<>();
    private ArrayList<String> bitAmounts = new ArrayList<>();
    private int budget = 0;
    ArrayList<String> monthlyUsed = new ArrayList<>();
    String[] months = new String[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tracker = new Intent(TrackerActivity.this, HomeActivity.class);
                startActivity(tracker);
            }

        });
        GetDailyBudget();//
        SetDailyGrid();//
        GetDailyBits();
        GetReadyToFillDailyGrid();//
        SetHeaderText();
        GetYearlyData();
        SetYearGrid();
    }//onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracker, menu);
        return true;
    }//onCreateOptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_menu_item)
        {
            Intent home = new Intent(TrackerActivity.this, StartActivity.class);
            startActivity(home);
        }
        else if (id == R.id.about_menu_item)
        {
            Intent about = new Intent(TrackerActivity.this, AboutActivity.class);
            startActivity(about);
        }
        else if (id == R.id.settings_menu_item)
        {
            Intent settings = new Intent(TrackerActivity.this, ResetActivity.class);
            startActivity(settings);
        }
        else
        {
            // oops
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()
    public void GetDailyBudget(){
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        int userID = 0;
        Cursor c = db.rawQuery("SELECT * FROM tblUser WHERE userType='MainUser'",null);
        if(c != null)
        {
            if (c.moveToFirst()) {
                userID = Integer.parseInt(c.getString(0));
            }
        }
        // get the monthly budget amount
        Cursor b = db.rawQuery("SELECT dailybudget FROM tblBudget WHERE userID=" +userID, null);
        if(b != null)
        {
            if (b.moveToFirst()) {
                budget = Integer.parseInt(b.getString(0));
            }
        }
    } // GetDailyBudget
    public void SetDailyGrid(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int wSize = 0;
        if(width >= 1440) {
            wSize = 42;
        }
        else {
            wSize = 30;
        }
        GridLayout layout = findViewById(R.id.daily_grid_layout_ID);
        for(int i = 26; i >= 0; i--) {
            for (int j = 0; j <= 31; j++) {
                TextView numberText = new TextView(this);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = wSize;
                lp.height = 30;
               // lp.leftMargin = 10;
                //lp.setGravity(Gravity.CENTER);
                numberText.setLayoutParams(lp);
                String rcId = "000" + i + "999" + j;
                numberText.setId(Integer.parseInt(rcId));
                // numberText.setLayoutParams(new android.view.ViewGroup.LayoutParams(30, 50));
                //image.setLayoutParams(new android.view.ViewGroup.LayoutParams(30, 20));

                if(i==0 && j== 0)
                {
                    numberText.setText("");
                    numberText.setTextSize(6);

                    layout.addView(numberText);
                }

                else{

                    if(i==0)
                    {
                        Date date = new Date();
                        String theDate = new SimpleDateFormat("dd").format(date);


                        numberText.setText(String.valueOf(j));
                        if(j==Integer.parseInt(theDate))
                        {
                            numberText.setBackgroundResource(R.color.Green4);
                            numberText.setTextColor(Color.WHITE);
                        }

                        if(j>=10)
                        {
                            numberText.setTextSize(6);
                            numberText.setPadding(5,0,0,0);

                        }
                        else{
                            numberText.setTextSize(7);
                            numberText.setPadding(10,0,0,0);
                        }
                        layout.addView(numberText);

                    }
                    else if(j== 0)
                    {
                        lp.leftMargin = 30;
                        if((i)%5 == 0 ) {
                            numberText.setText(String.valueOf(i));

                        }
                        if(i==budget)
                        {
                            numberText.setText(String.valueOf(i));
                            numberText.setTextColor(Color.rgb(255,0,0));
                        }
                        numberText.setTextSize(8);
                        numberText.setPadding(0,0,0,0);
                        layout.addView(numberText);
                    }
                    else{
                        if(j%2 == 0) {
                            numberText.setBackgroundResource(R.color.Green5);
                        }
                        else{
                            numberText.setBackgroundResource(R.color.Red4);
                        }
                        if((i)%5 == 0)
                        {
                            numberText.setText("-----");
                            numberText.setPadding(0,0,0,0);
                            if(i == budget)
                            {
                                //numberText.setText("-----");
                                numberText.setBackgroundResource(R.drawable.red_minus_icon);
                                //numberText.setTextColor(Color.rgb(255,0,0));
                               // layout.addView(numberText);
                            }
                        }
                        else if(i == budget)
                        {
                            //numberText.setText("-----");
                            //numberText.setTextColor(Color.rgb(255,0,0));
                            numberText.setBackgroundResource(R.drawable.red_minus_icon);
                           // layout.addView(numberText);
                        }
                        else{
                            numberText.setText("");
                        }

                        //numberText.setText("");
                        numberText.setTextSize(8);
                        //numberText.setPadding(8,0,0,0);
                        layout.addView(numberText);
                    }
                }
            }//for j
        }//for i
    } // SetDailyGrid()
    public void GetDailyBits(){
        Date date = new Date();
        String theDate = new SimpleDateFormat("yyyy-MMM-dd").format(date);
        String datePart = theDate.substring(0, 8);
        String curDay;
        String curAmount;

        // create db it if does not exist
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        Cursor c = db.rawQuery("SELECT * FROM tblTracker WHERE trackerDate LIKE '" +
                datePart + "%'", null);

        if(c != null) {
            if (c.moveToFirst()) {
                do {
                    curDay = c.getString(1).substring(9, 11);
                    bitDates.add(curDay);
                    curAmount = c.getString(2);
                    bitAmounts.add(curAmount);
                }while(c.moveToNext());
            }
        }
        db.close();
    } // GetDailyBits()
    public void GetReadyToFillDailyGrid(){
        int curAmount;
        int total;
        String prevDay;
        String curDay;
        boolean paintedToday = false;
        if(bitDates.size() > 0)
        {
            prevDay = bitDates.get(0);
            total = Integer.parseInt(bitAmounts.get(0));

            for(int i = 1; i < bitDates.size(); i++)
            {
                curDay = bitDates.get(i);
                if(curDay.equals(prevDay)) {
                    total += Integer.parseInt(bitAmounts.get(i));
                    paintedToday = false;
                }
                else {
                    FillDailyGrid(Integer.parseInt(prevDay), total, budget);
                    prevDay = bitDates.get(i);
                    curAmount = Integer.parseInt(bitAmounts.get(i));
                    total = 0;
                    total += curAmount;
                }
            } // for

            if(total > 25)
            {
                total=25;
            }
            if(!paintedToday) {
                FillDailyGrid(Integer.parseInt(prevDay), total, budget);
            }
        } // if
    } // GetreadyToFillDailyGrid()
    public void SetHeaderText() {
        // get the daily budget
        // create db it if does not exist
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        String userName = "";
        Cursor c = db.rawQuery("SELECT * FROM tblUser WHERE userType='MainUser'",null);
        if(c != null)
        {
            if (c.moveToFirst()) {
                userName = c.getString(1);
            }
        }

        Date date = new Date();
        String theMonth = new SimpleDateFormat("M").format(date);
        String theYear = new SimpleDateFormat("YYYY").format(date);
        String month = "";
        switch (Integer.parseInt(theMonth)) {
            case 1: {
                month = "January";
                break;
            }
            case 2: {
                month = "February";
                break;
            }
            case 3: {
                month = "March";
                break;
            }
            case 4: {
                month = "April";
                break;
            }
            case 5: {
                month = "May";
                break;
            }
            case 6: {
                month = "June";
                break;
            }
            case 7: {
                month = "July";
                break;
            }
            case 8: {
                month = "August";
                break;
            }
            case 9: {
                month = "September";
                break;
            }
            case 10: {
                month = "October";
                break;
            }
            case 11: {
                month = "November";
                break;
            }
            case 12: {
                month = "December";
                break;
            }
        }

        // set the horizontal header text
        TextView header_text = findViewById(R.id.daily_header_layout_ID);
        header_text.setText(month + " " + theYear);
        // set the daily header text
        TextView daily_header_text = findViewById(R.id.user_header_layout_ID);
        daily_header_text.setText("Daily Tracking for " + userName.toUpperCase());

        // calc some vals and stuff
        int monthlyAllowance = budget * 31; // could be better ;)
        int totalBitsUsed = 0;

        for(int i = 0; i < bitAmounts.size(); i++)
        {
            totalBitsUsed += Integer.parseInt(bitAmounts.get(i));
        }

        // Get the bits used today
        String theDate = new SimpleDateFormat("yyyy-MMM-dd").format(date);
        int dayAmount = 0;

        Cursor d = db.rawQuery("SELECT * FROM tblTracker WHERE trackerDate LIKE '" +
                theDate + "%'", null);

        if(d != null) // no records
        {
            if (d.moveToFirst())
            {
                do {
                    dayAmount += Integer.parseInt(d.getString(2));
                }while(d.moveToNext());
            }
        }
        db.close();

        TextView tracker_details_text = findViewById(R.id.daily_details_layout_ID);
        tracker_details_text.setText("Bits today: " + dayAmount +
                "  |  Used this month: " + totalBitsUsed +  " of " + monthlyAllowance);
    } // SetHeaderText
    public void FillDailyGrid(int day, int amount, int curBudget) {
        String bitID;
        for(int i = 1; i <= amount; i++)
        {
            bitID = "000" + i + "999" + day;
            TextView tv = findViewById(Integer.parseInt(bitID));
            if(amount <= curBudget) {
                //tv.setBackground(getDrawable(R.color.green5));
                tv.setBackgroundResource(R.color.Green3);
            }
            else {
                tv.setBackgroundResource(R.color.Red1);
                //tv.setBackground(getDrawable(R.color.red5));
            }
        }
    } // FillDailyGrid
    public void GetYearlyData() {
        // create db it if does not exist
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        months[0] = "Jan"; months[1] = "Feb"; months[2] = "Mar";
        months[3] = "Apr"; months[4] = "May"; months[5] = "Jun";
        months[6] = "Jul"; months[7] = "Aug"; months[8] = "Sep";
        months[9] = "Oct"; months[10] = "Nov"; months[11] = "Dec";

        int curTotal = 0;

        for(int i = 0; i < 12; i++)
        {
            Cursor c = db.rawQuery("SELECT * FROM tblTracker WHERE trackerDate LIKE '%" +
                    months[i] + "%'", null);

            if(c != null)
            {
                if (c.moveToFirst())
                {
                    do {
                        curTotal += Integer.parseInt(c.getString(2));
                    }while(c.moveToNext());
                    monthlyUsed.add(String.valueOf(curTotal));
                }
                else
                {
                    monthlyUsed.add(String.valueOf(0));
                }
            }
            curTotal = 0;
        }
        db.close();
    }// GetYearlyData
    public void SetYearGrid(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int wSize = 0;
        if(width >= 1440) {
            wSize = 103;
        }
        else {
            wSize = 78;
        }
        GridLayout layout = findViewById(R.id.yearly_grid);
        for(int i = 0; i < 12; i++) {
            TextView theMonth = new TextView(this);
            theMonth.setText(months[i]);
            theMonth.setTextSize(13);
            theMonth.setPadding(11,21,0,42);
            if(i % 2 != 0) {
                theMonth.setBackgroundResource(R.color.Even);
            }
            else {
                theMonth.setBackgroundResource(R.color.Odd);
            }
            Date anotherDate = new Date();
            String monthNum = new SimpleDateFormat("M").format(anotherDate);
            if( i == Integer.parseInt(monthNum) - 1) {
                theMonth.setBackgroundResource(R.color.Green4);
                theMonth.setTextColor(Color.WHITE);
            }
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 109;
            lp.setGravity(Gravity.CENTER);
            theMonth.setLayoutParams(lp);
            layout.addView(theMonth);
        }
        for(int j = 1; j <= 12; j++) {
            TextView monthText = new TextView(this);
            monthText.setText(monthlyUsed.get(j - 1));
            if(j % 2 == 0) {
                monthText.setBackgroundResource(R.color.Red4);
            }
            else {
                monthText.setBackgroundResource(R.color.Green1);
            }
            monthText.setTextSize(15);
            monthText.setPadding(0,99,0,99);
            monthText.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 109;
            monthText.setLayoutParams(lp);
            layout.addView(monthText);
        }
        for(int k = 0; k < 12; k++) {
            TextView budgetText = new TextView(this);
            int monthBudget = budget * 31;
            budgetText.setPadding(13,42,0,21);
            budgetText.setText(monthBudget + "");
            budgetText.setTextSize(14);
            if(k % 2 != 0) {
                budgetText.setBackgroundResource(R.color.Even);
            }
            else {
                budgetText.setBackgroundResource(R.color.Odd);
            }
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 109;
            lp.setGravity(Gravity.CENTER);
            budgetText.setLayoutParams(lp);
            layout.addView(budgetText);
        }
    } // SetYearGrid
} //Tracker Activity
