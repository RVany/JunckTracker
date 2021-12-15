package com.appsky.junktracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tankyou for Setting JunkTracker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }//onCreate

    public void setBudget(View v)
    {
        // create db it if does not exist or open it.
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        EditText usernameEditText = findViewById(R.id.user_name_layout_ID);
        String username = usernameEditText.getText().toString();
        EditText dailybudgetEditText = findViewById(R.id.daily_budget_layout_ID);
        String dailybudget = dailybudgetEditText.getText().toString();

        if(!isValidUserName(username)){
            usernameEditText.setError("Invalid User Name");
            Toast.makeText(this, "Please enter 6 to 10 characters for User Name!", Toast.LENGTH_LONG).show();
        }
        else if(!isValidDailyBudget(dailybudget)){
            dailybudgetEditText.setError("Invalid Daily Budget");
            Toast.makeText(this, "Please enter a number between 1 to 8 for Daily Budget!", Toast.LENGTH_LONG).show();
        }
        else{
            String todayDate = getTodayDate();
            db.execSQL("INSERT INTO tblUser VALUES(?1, + '" + username +"', 'MainUser');");
            Cursor c = db.rawQuery("SELECT * FROM tblUser WHERE userName = '"+ username + "' AND "+
                    "userType='MainUser';", null);
            int userID = 0;
            if(c != null) {
                if (c.moveToFirst()) {
                    do {
                        userID = Integer.parseInt(c.getString(0));
                    }while(c.moveToNext());
                }
            }
            db.execSQL("INSERT INTO tblBudget VALUES(?1, + '" + todayDate +"',"+Integer.parseInt(dailybudget)+","+userID+");");

            Cursor e = db.rawQuery("SELECT * FROM tblUser", null);
            Cursor d = db.rawQuery("SELECT * FROM tblBudget", null);


            if(e != null && d != null)
            {
               if(e.getCount() <= 0 || d.getCount() <=0)
                {
                    Intent settingsPage = new Intent(SettingsActivity.this, SettingsActivity.class);
                    startActivity(settingsPage);
                }
                else
                {

                    if (d.moveToFirst()) {
                        Toast.makeText(this, "Your Daily Budget is " + d.getString(2),
                                Toast.LENGTH_LONG).show();
                    }
                    Intent HomePage = new Intent(SettingsActivity.this, HomeActivity.class);
                    startActivity(HomePage);

                }

            }
            db.close();
        }
    }//setBudget
    private boolean isValidUserName(String username) {
        if (username.length() >= 6) {
            return true;
        }
        else{
            return false;
        }
    }//isValidUserName
    private boolean isValidDailyBudget(String dailybudget) {
        if (dailybudget.length()> 0 && Integer.parseInt(dailybudget) > 0 && Integer.parseInt(dailybudget) <= 25 ) {
            return true;
        }
        else{
            return false;
        }
    }//isValidDailyBudget
    private String getTodayDate(){
        Date date = new Date();
        String todayDate = new SimpleDateFormat("yyyy-MMM-dd").format(date);
        return todayDate;
    }//getTodayDate()
}//SettingsActivity
