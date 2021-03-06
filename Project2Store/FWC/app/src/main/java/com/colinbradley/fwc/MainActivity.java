package com.colinbradley.fwc;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.RecyclerViewForGear.Adapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemSelectedListener{
    private List<FWCGear> mGearList;
    private Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private AsyncTask<Void,Void,Void> mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SETS UP RECYCLERVIEW LAYOUT
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        //** METHOD TO START TASK TO CHECK/ADD ITEMS TO DB***************
        fillDataBase();

    }

    public void fillDataBase(){
        final DatabaseHelper db = DatabaseHelper.getInstance(this);

        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(this, "Still adding data to the database. Please wait",
                    Toast.LENGTH_LONG).show();
        }else {
            mTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    if (db.getAllAsList().size() == 0) {
                        db.populateGearTable();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mGearList = db.getAllAsList();
                    mAdapter = new Adapter(mGearList,MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }
            };
        mTask.execute();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            List<FWCGear> searchGear = DatabaseHelper.getInstance(this).searchGear(query);
            mAdapter.replaceData(searchGear);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //CREATES SEARCH BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_view).getActionView();
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    //SENDS ID TO ITEMDETAILACTIVITY WHEN CLICKED
    @Override
    public void onItemSelected(int id) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ID_KEY, id);
        startActivity(intent);
    }
}
