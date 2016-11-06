package com.colinbradley.fwc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ImageConverter;
import com.colinbradley.fwc.RecyclerView.Adapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemSelectedListener{
    private DatabaseHelper mHelper;
    private List<FWCGear> mGearList;
    private Adapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DatabaseHelper db = DatabaseHelper.getInstance(this);


        Log.d("INSERT: ", "INSERTING....");
        db.addGear(new FWCGear(1,"NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));


        mGearList = db.getAllAsList();
        mAdapter = new Adapter(mGearList,this);
        mRecyclerView.setAdapter(mAdapter);




    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int id) {

    }
}
