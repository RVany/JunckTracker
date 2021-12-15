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
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start JunkTracker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        userDbSetup();
    }//onCreate
    public void userDbSetup()
    {
        // create db it if does not exist or open it.
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);

        /* create our tblProfile if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblProfile" +
                "(profileID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "profileStatus VARCHAR, profileBudget INTEGER);");*/

        // create tblUser if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblUser" +
                "(userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userName VARCHAR, userType VARCHAR);");

        // create tblBudget if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblBudget" +
                "(budgetID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "budgetDate VARCHAR, dailybudget INTEGER, userID INTEGER);");

        // create tblTracker if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblTracker" +
                "(trackerID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "trackerDate VARCHAR, bitAmount INTEGER, userID INTEGER);");

        Cursor c = db.rawQuery("SELECT * FROM tblUser", null);
        Cursor d = db.rawQuery("SELECT * FROM tblBudget", null);

        if(c != null && d != null)
        {

            if(c.getCount() <= 0 || d.getCount() <=0)
            {
                Intent settingsPage = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(settingsPage);
            }
            else
            {
               /* if (d.moveToFirst()) {
                    Toast.makeText(this, "Your Daily Budget is " + d.getString(2),
                            Toast.LENGTH_LONG).show();
                }*/

                Intent HomePage = new Intent(StartActivity.this, HomeActivity.class);
                startActivity(HomePage);
            }
        }
        else{
            Toast.makeText(this, "Something Went Wrong!" ,
                    Toast.LENGTH_LONG).show();
        }
    }


}//StartActivity
