package com.appsky.junktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView mLink = (TextView) findViewById(R.id.link);
        if (mLink != null) {
            mLink.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }//onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }//onCreateOptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_menu_item)
        {
            Intent home = new Intent(AboutActivity.this, StartActivity.class);
            startActivity(home);
        }
        else if (id == R.id.tracker_menu_item)
        {
            Intent tracker = new Intent(AboutActivity.this, TrackerActivity.class);
            startActivity(tracker);
        }
        else if (id == R.id.settings_menu_item)
        {
            Intent settings = new Intent(AboutActivity.this, ResetActivity.class);
            startActivity(settings);
        }
        else
        {
            // oops
        }
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected()
}
