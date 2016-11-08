package com.colinbradley.fwc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ShoppingCartSingleton;
import com.colinbradley.fwc.RecyclerViewForCart.CartAdapter;

import java.util.LinkedList;

public class ShoppingCartActivity extends AppCompatActivity {

    public static LinkedList<FWCGear> mShoppingCartList;
    private CartAdapter mCAdapter;
    private RecyclerView mRecyclerView;
    TextView mTotalPrice;
    Button mUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.shopping_cart_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        final ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();

        mShoppingCartList = cart.getCart();

        mCAdapter = new CartAdapter(mShoppingCartList);
        mRecyclerView.setAdapter(mCAdapter);


        mUpdateButton = (Button)findViewById(R.id.update_price_button);
        mTotalPrice = (TextView)findViewById(R.id.total_price);
        //cart.updateTotalPrice();
        String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
        mTotalPrice.setText(newPrice);




                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Thank you for your purchase :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mShoppingCartList.clear();
                mCAdapter.notifyDataSetChanged();
                //cart.updateTotalPrice();
                String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
                mTotalPrice.setText(newPrice);

            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
                mTotalPrice.setText(newPrice);
            }
        });

        //updatePrice();

    }

    public void updatePrice(){
        ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
        String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
        mTotalPrice.setText(newPrice);
        checkForChanges();
    }

    public void checkForChanges(){
        ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
        LinkedList<FWCGear> tempList;
        tempList = cart.getCart();

        if (mShoppingCartList.size() == tempList.size()){

        }else {
            updatePrice();
        }

    }

/*
    public void updateTotalPrice(){
        int totalPrice = getTotalPrice();
        String priceAsString = Integer.toString(totalPrice);

        mTotalPrice.setText(priceAsString);
    }

    public int getTotalPrice(){
        int i;
        int add = 0;
        for (i = 0; i < mShoppingCartList.size(); i++){
            int itemPrice = mShoppingCartList.get(i).getPrice();
            add = add + itemPrice;
        }
        return add;
    }
    */

}
