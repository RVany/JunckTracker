package com.appsky.junktracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
public class HomeActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";
    private int budget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setSubtitle("     S t o p     t h e    J u n k !");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tracker = new Intent(HomeActivity.this, TrackerActivity.class);
                startActivity(tracker);
            }
        });
        junkFoodDbSetup();
        setCat();
        GetDailyBudget();
       // Toast.makeText(this, budget, Toast.LENGTH_LONG).show();
    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }//onCreateOptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.about_menu_item)
            {
                Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(about);
            }
            else if (id == R.id.tracker_menu_item)
            {
                Intent tracker = new Intent(HomeActivity.this, TrackerActivity.class);
                startActivity(tracker);
            }
            else if (id == R.id.settings_menu_item)
            {
                Intent settings = new Intent(HomeActivity.this, ResetActivity.class);
                startActivity(settings);
            }
            else
            {
                // oops
            }
            return super.onOptionsItemSelected(item);
        } // onOptionsItemSelected()
    //////////////////////////////////// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  ////////////////////////
    public void junkFoodDbSetup() {
        // create db it if does not exist or open it.
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        // create our tblCat if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblCat" +
                "(catID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "catName VARCHAR);");
        // create our tblItem if it does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS tblItem" +
                "(itemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemName VARCHAR, bitAmount INTEGER, catID INTEGER);");
        Cursor c = db.rawQuery("SELECT * FROM tblCat", null);
        if(c != null) // no records
        {
            if(c.getCount() <= 0) {
                // insert our cats
                db.execSQL("INSERT INTO tblCat VALUES(?1, 'Snacks');");
                db.execSQL("INSERT INTO tblCat VALUES(?1, 'Drinks');");
                db.execSQL("INSERT INTO tblCat VALUES(?1, 'Fast Food');");
                db.execSQL("INSERT INTO tblCat VALUES(?1, 'Combos');");
                db.execSQL("INSERT INTO tblCat VALUES(?1, 'Custom +');");
                // insert our items
                // SNACKS
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Chips', 1, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Candy', 1, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Donut', 2, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Muffin', 2, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Chocolate Bar', 3, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Ice Cream', 3, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Pie', 2, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Cake', 2, 1);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Cookies',3, 1);");
                // DRINKS
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Pop', 3, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Coffee', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Tea', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Hot Chocolate', 3, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Juice', 3, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Beer', 3, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Wine', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Mixed Drink', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Milkshake', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Energy drink', 2, 2);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Flavored Water', 1, 2);");
                // FAST FOOD
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Pizza', 1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Fries', 1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Poutin', 3, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Patty',  1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Wings', 1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Taco', 1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Hot Dog', 1, 3);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Chicken Nuggets', 1, 3);");
                // COMBOS
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Wings & Beer', 5, 4);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Burger & Fries', 4, 4);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Pizza & Beer', 4, 4);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Popcorn Pop & Candy',  4, 4);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Coffee & Donut',  3, 4);");
                db.execSQL("INSERT INTO tblItem VALUES(?1, 'Pizza Wings & Beer', 10, 4);");
                /*
                // insert some TEST DATA
                // Apr
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Apr-20', 6, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Apr-21', 3, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Apr-22', 5, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Apr-23', 9, 1);");
                // May
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-May-20', 3, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-May-21', 4, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-May-22', 6, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-May-23', 2, 1);");
                // Jun
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Jun-15', 4, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Jun-16', 4, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Jun-19', 8, 1);");
                db.execSQL("INSERT INTO tblTracker VALUES(?1, '2018-Jun-22', 4, 1);");*/
            }
        }
        db.close(); // close db connection, obviously
    } // junkFoodDbSetup()
    public void setCat() {
        LinearLayout ll = findViewById(R.id.cat_linear_layout_ID);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // create db it if does not exist or open it.
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM tblCat" , null);
        //final String categoryName = c.getString(1);
        int count;
        if(c != null) {
            if (c.moveToFirst()) {
                do {
                    Button catButton = new Button(this);
                    catButton.setText(c.getString(1));
                    count = Integer.parseInt(c.getString(0));
                    //final String categoryName = c.getString(1);
                    catButton.setId(count);
                    if(count == 1)
                    {
                        catButton.setBackground(getDrawable(R.color.Red1));
                    }
                    if(count == 2)
                    {
                        catButton.setBackground(getDrawable(R.color.Red2));
                    }
                    if(count == 3)
                    {
                        catButton.setBackground(getDrawable(R.color.Green1));
                    }
                    if(count == 4)
                    {
                        catButton.setBackground(getDrawable(R.color.Green2));
                    }
                    if(count == 5)
                    {
                        catButton.setBackground(getDrawable(R.color.Green3));
                        catButton.setTextColor(Color.rgb(255,0,0));
                    }
                    catButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String category = String.valueOf(v.getId());
                            Intent itemPage = new Intent(HomeActivity.this, ItemActivity.class);
                            itemPage.putExtra("selCat", category);
                            //itemPage.putExtra("selCat", categoryName);
                            startActivity(itemPage);
                        }
                    });
                    ll.addView(catButton, lp);
                    count++;
                }while(c.moveToNext());
            }
        }
    }//setCat
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
            Toast.makeText(this, "Your Current Daily Budget is " + String.valueOf(budget),Toast.LENGTH_LONG).show();
        }
    } // GetDailyBudget
}//HomeActivity



