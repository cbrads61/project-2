package com.colinbradley.fwc.RecyclerViewForCart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.colinbradley.fwc.R;

/**
 * Created by colinbradley on 11/7/16.
 */

public class CartHolder extends RecyclerView.ViewHolder{

    TextView mName, mType, mPrice;
    ImageView mImage;
    Button mAddOne, mRemoveOne;
    View mRootView;

    public CartHolder(View itemView) {
        super(itemView);
        mName = (TextView)itemView.findViewById(R.id.gear_name_cart);
        mType = (TextView)itemView.findViewById(R.id.gear_type_cart);
        mImage = (ImageView)itemView.findViewById(R.id.gear_photo_cart);
        mAddOne = (Button)itemView.findViewById(R.id.add1button);
        mRemoveOne = (Button)itemView.findViewById(R.id.remove1button);
        mPrice = (TextView)itemView.findViewById(R.id.price_cart);
        mRootView = itemView;
    }
}
