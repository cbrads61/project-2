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

        //SET UP RECYCLERVIEW LAYOUT
        mRecyclerView = (RecyclerView)findViewById(R.id.shopping_cart_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        final ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();

        mShoppingCartList = cart.getCart();

        //SET INFO AND ADAPTER TO RECYCLERVIEW
        mCAdapter = new CartAdapter(mShoppingCartList);
        mRecyclerView.setAdapter(mCAdapter);
        
        mUpdateButton = (Button)findViewById(R.id.update_price_button);
        mTotalPrice = (TextView)findViewById(R.id.total_price);
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
                String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
                mTotalPrice.setText(newPrice);
            }
        });

        //UPDATE CART TO HAVE THE TOTAL PRICE REFLECT ANY CHANGES MADE TO THE CART WHILE STILL IN THE CART
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPrice = cart.returnNewPrice(cart.updateTotalPrice());
                mTotalPrice.setText(newPrice);
            }
        });
    }
}
