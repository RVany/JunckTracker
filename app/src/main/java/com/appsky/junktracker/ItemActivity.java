package com.appsky.junktracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private final String DB_NAME = "JunkTrackerDB";

    private static String category = "";
    private static String categoryName = "";
    private ArrayList<String> itemNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        ArrayAdapter<String> items_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        ListView items_list = findViewById(R.id.items_list);

        ImageView menu_image = findViewById(R.id.menu_Image_layout_ID);
        ImageView menu_image2 = findViewById(R.id.menu_Image_layout2_ID);
        ImageView menu_image3 = findViewById(R.id.menu_Image_layout3_ID);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getString("selCat") != "" )
        {
            category = extras.getString("selCat").toString();
            if(category.equals("1"))
            {
                categoryName="SNACKS";
                items_list.setBackgroundResource(R.color.Red1);
                menu_image.setImageResource(R.drawable.snacks2 );
                menu_image2.setImageResource(R.drawable.candybar);
                menu_image3.setImageResource(R.drawable.donut );
            }
            else if(category.equals("2"))
            {
                categoryName="DRINKS";
                items_list.setBackgroundResource(R.color.Red2);
                menu_image.setImageResource(R.drawable.drink3);
                menu_image2.setImageResource(R.drawable.drinks);
                menu_image3.setImageResource(R.drawable.drinks1 );
            }
            else if(category.equals("3"))
            {
                categoryName="FAST FOOD";
                items_list.setBackgroundResource(R.color.Green1);
                menu_image.setImageResource(R.drawable.sandwitch);
                menu_image2.setImageResource(R.drawable.pizza);
                menu_image3.setImageResource(R.drawable.hotdog );
            }
            else if(category.equals("4"))
            {
                categoryName="COMBOS";
                items_list.setBackgroundResource(R.color.Green2);
                menu_image.setImageResource(R.drawable.combo);
                menu_image2.setImageResource(R.drawable.combo2);
                menu_image3.setImageResource(R.drawable.combo3 );
            }
            else {
                categoryName="CUSTOM+(Coming Soon!)";
                items_list.setBackgroundResource(R.color.Red3);
                menu_image.setImageResource(R.drawable.custom_food);
                menu_image2.setImageResource(R.drawable.custom_food2);
                menu_image3.setImageResource(R.drawable.samosa );

            }
            //categoryName = extras.getString("selCatName");
            //setTitle(category);
            setTitle(categoryName);

        }
        else
        {
            setTitle("Somthing Went Wrong");

        }
        GetItems(category);
        items_list.setAdapter(items_adapter);
        // Create a message handler
        items_list.setOnItemClickListener(mItemClicked);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tracker = new Intent(ItemActivity.this, TrackerActivity.class);
                startActivity(tracker);
            }
        });
    }//onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }//onCreateOptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_menu_item)
        {
            Intent home = new Intent(ItemActivity.this, StartActivity.class);
            startActivity(home);
        }
        else if (id == R.id.about_menu_item)
        {
            Intent about = new Intent(ItemActivity.this, AboutActivity.class);
            startActivity(about);
        }
        else if (id == R.id.tracker_menu_item)
        {
            Intent tracker = new Intent(ItemActivity.this, TrackerActivity.class);
            startActivity(tracker);
        }
        else if (id == R.id.settings_menu_item)
        {
            Intent settings = new Intent(ItemActivity.this, ResetActivity.class);
            startActivity(settings);
        }
        else
        {
            // oops
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()
    private AdapterView.OnItemClickListener mItemClicked =
            new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView parent, View v,
                                        int position, long id)
                {
                    String fullItem = itemNames.get(position);
                    String curChar;
                    String bitAmount;
                    int startIndex = 0;
                    for(int i = fullItem.length() - 1; i > 0; i--)
                    {
                        curChar = fullItem.substring(i, i + 1);
                        if(curChar.equals("("))
                        {
                            startIndex = i;
                        }
                    }
                    bitAmount = fullItem.substring(startIndex + 1,
                            fullItem.length() - 1);

                    TrackItem(bitAmount);
                }
            };
    public void TrackItem(String bitAmount)
    {
        // insert a tracker record
        // with the correct bit amount
        Date date = new Date();
        String theDate = new SimpleDateFormat("yyyy-MMM-dd").format(date);
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db.execSQL("INSERT INTO tblTracker VALUES(?1,'" +
                theDate + "'," + bitAmount + ", 1)");
        Toast.makeText(this, bitAmount + " bit(s) added!", Toast.LENGTH_LONG).show();
    }
    public void GetItems(String cat) {
        String curItem;
        db = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM tblItem WHERE catID=" +
                cat, null);
        if(c != null) {
            if(c.moveToFirst()) {
                do {
                    if(Integer.parseInt(c.getString(3))== 4){
                        curItem = c.getString(1).toUpperCase()+" - Combo( "+
                                c.getString(2)+ " )";
                        itemNames.add(curItem);
                    }
                    else{
                        curItem = c.getString(1).toUpperCase()+" - Small( "+
                                c.getString(2)+ " )";
                        itemNames.add(curItem);
                        curItem = c.getString(1).toUpperCase()+" - Regular ( "+
                                Integer.parseInt(c.getString(2))*2+ " )";
                        itemNames.add(curItem);
                        curItem = c.getString(1).toUpperCase()+" - Large ( "+
                                Integer.parseInt(c.getString(2))*3+ " )";
                        itemNames.add(curItem);
                    }
                }while(c.moveToNext());
            }
        }
        db.close();
        if(cat.equals("5"))
        {
            Toast.makeText(this, "You can Add your own Items Here!(Coming Soon!)", Toast.LENGTH_LONG).show();
        }
    }
}//ItemActivity!
