package com.colinbradley.fwc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ShoppingCartSingleton;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String ID_KEY = "idkey";

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

        DatabaseHelper helper = DatabaseHelper.getInstance(this);
        final FWCGear selectedGear = helper.getItembyID(selectedItemID);

        TextView name = (TextView)findViewById(R.id.name);
        TextView description = (TextView)findViewById(R.id.description);
        TextView type = (TextView)findViewById(R.id.type);
        TextView price = (TextView)findViewById(R.id.itemprice);
        ImageView gearImage = (ImageView)findViewById(R.id.picture);

        //GET THE INT FOR PRICE FROM DATABASE AND MAKE A STRING
        int priceAsInt = selectedGear.getPrice();
        String priceAsString = Integer.toString(priceAsInt);

        name.setText(selectedGear.getName());
        type.setText(selectedGear.getType());
        description.setText(selectedGear.getDescription());
        price.setText(priceAsString);
        gearImage.setBackgroundResource(selectedGear.getImage());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added To Cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
                cart.addGear(selectedGear);
            }
        });

    }

}
