package com.colinbradley.fwc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ShoppingCartSingleton;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String ID_KEY = "idkey";

    private int mItemID;

    private AsyncTask<Void,Void,FWCGear> mGatherDetailsTask;

    private AsyncTask<Void,Void,Void> mAddToCartTask;

    TextView mName, mDescription, mType, mPrice;
    ImageView mGearImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int selectedItemID = getIntent().getIntExtra(ID_KEY, -1);
        if (selectedItemID == -1){
            finish();
        }
        mItemID = selectedItemID;

        mName = (TextView)findViewById(R.id.name);
        mDescription = (TextView)findViewById(R.id.description);
        mType = (TextView)findViewById(R.id.type);
        mPrice = (TextView)findViewById(R.id.itemprice);
        mGearImage = (ImageView)findViewById(R.id.picture);

        gatherItemDetails(selectedItemID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added To Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addToCart();
            }
        });
    }

    public void gatherItemDetails(int itemID){
        if (mGatherDetailsTask != null && mGatherDetailsTask.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(this, "Gathering Data...Please Wait",
                    Toast.LENGTH_LONG).show();
        }else {
            mGatherDetailsTask = new AsyncTask<Void, Void, FWCGear>() {
                @Override
                protected FWCGear doInBackground(Void... voids) {
                    DatabaseHelper helper = DatabaseHelper.getInstance(ItemDetailActivity.this);
                    final FWCGear selectedGear = helper.getItembyID(mItemID);
                    return selectedGear;
                }

                @Override
                protected void onPostExecute(FWCGear selectedGear) {
                    super.onPostExecute(selectedGear);
                    mName.setText(selectedGear.getName());
                    mType.setText(selectedGear.getType());
                    mDescription.setText(selectedGear.getDescription());

                    int priceAsInt = selectedGear.getPrice();
                    String priceAsString = Integer.toString(priceAsInt);

                    mPrice.setText(priceAsString);
                    mGearImage.setBackgroundResource(selectedGear.getImage());
                }
            };
            mGatherDetailsTask.execute();
        }
    }

    public void addToCart(){
        if (mAddToCartTask != null && mAddToCartTask.getStatus() == AsyncTask.Status.RUNNING){
            Toast.makeText(this, "Adding To Cart...Please Wait",
                    Toast.LENGTH_LONG).show();
        }else{
            mAddToCartTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
                    DatabaseHelper helper = DatabaseHelper.getInstance(ItemDetailActivity.this);
                    cart.addGear(helper.getItembyID(mItemID));
                    return null;
                }
            };
            mAddToCartTask.execute();
        }
    }
}
