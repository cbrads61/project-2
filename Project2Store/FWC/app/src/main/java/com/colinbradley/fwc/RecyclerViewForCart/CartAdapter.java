package com.colinbradley.fwc.RecyclerViewForCart;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ShoppingCartSingleton;
import com.colinbradley.fwc.R;
import com.colinbradley.fwc.ShoppingCartActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by colinbradley on 11/7/16.
 */

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {

    private List<FWCGear> mShoppingCartList;

    public CartAdapter(List<FWCGear> mShoppingCartList) {
        this.mShoppingCartList = mShoppingCartList;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        final FWCGear currentItem = mShoppingCartList.get(position);

        holder.mImage.setBackgroundResource(mShoppingCartList.get(position).getImage());
        holder.mName.setText(mShoppingCartList.get(position).getName());
        holder.mType.setText(mShoppingCartList.get(position).getType());

        int priceAsInt = mShoppingCartList.get(position).getPrice();
        final String priceAsString = Integer.toString(priceAsInt);

        holder.mPrice.setText(priceAsString);

        //ADDS ANOTHER OF THE SAME ITEM TO CART
        holder.mAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
                cart.addGear(currentItem);
                notifyDataSetChanged();
            }
        });

        //REMOVES ITEM FROM CART
        holder.mRemoveOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartSingleton cart = ShoppingCartSingleton.getInstance();
                cart.removeGear(currentItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShoppingCartList.size();
    }
}
