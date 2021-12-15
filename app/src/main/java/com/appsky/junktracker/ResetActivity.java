package com.appsky.junktracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";
    private int budget = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        GetDailyBudget();
        TextView currentBudget = findViewById(R.id.current_budget_text);
        currentBudget.setText("Current: " + budget);
    } // onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reset, menu);
        return true;
    }//onCreateOptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home_menu_item)
        {
            Intent home = new Intent(ResetActivity.this, StartActivity.class);
            startActivity(home);
        }
        else if (id == R.id.about_menu_item)
        {
            Intent about = new Intent(ResetActivity.this, AboutActivity.class);
            startActivity(about);
        }
        else if (id == R.id.tracker_menu_item)
        {
            Intent settings = new Intent(ResetActivity.this, TrackerActivity.class);
            startActivity(settings);
        }
        else
        {
            // oops
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()
    public void ResetAppData(View v)  {
        Toast.makeText(this, getString(R.string.reset_warning_text), Toast.LENGTH_LONG).show();
        // create db it if does not exist
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        db.delete("tblUser", null, null);
        db.delete("tblTracker", null, null);
        db.delete("tblBudget", null, null);
        db.close();
        Intent startPage = new Intent(ResetActivity.this, SettingsActivity.class);
        startActivity(startPage);
    } // ResetAppData()
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
        //Toast.makeText(this, "UserID " + userID ,Toast.LENGTH_LONG).show();
        // get the monthly budget amount
        Cursor b = db.rawQuery("SELECT dailybudget FROM tblBudget WHERE userID=" +userID, null);
        if(b != null)
        {
            if (b.moveToFirst()) {
                budget = Integer.parseInt(b.getString(0));
            }
        }
    } // GetDailyBudget
    public void EditBudget(View v){
        EditText newBudget = findViewById(R.id.bit_budget_amount);
        int newBudgetInt = Integer.parseInt(newBudget.getText().toString());

        // create db it if does not exist
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        int userID = 0;
        Cursor c = db.rawQuery("SELECT * FROM tblUser WHERE userType='MainUser'",null);
        if(c != null)
        {
            if (c.moveToFirst()) {
                userID = Integer.parseInt(c.getString(0));
            }
        }

        db.execSQL("UPDATE tblBudget SET dailybudget= " + newBudgetInt +
                " WHERE userID="+userID);

        Intent homePage = new Intent(ResetActivity.this, HomeActivity.class);
        startActivity(homePage);
    } // EditBudget

} // ResetActivity
