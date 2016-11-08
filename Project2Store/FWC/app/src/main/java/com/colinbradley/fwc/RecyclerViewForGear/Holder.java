package com.colinbradley.fwc.RecyclerViewForGear;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colinbradley.fwc.R;

/**
 * Created by colinbradley on 11/5/16.
 */

public class Holder extends RecyclerView.ViewHolder {

    TextView mName, mType;
    ImageView mImage;
    View mRootView;

    public Holder(View itemView) {
        super(itemView);

        mName = (TextView)itemView.findViewById(R.id.gear_name);
        mType = (TextView)itemView.findViewById(R.id.gear_type);
        mImage = (ImageView) itemView.findViewById(R.id.gear_photo);
        mRootView = itemView;

    }

}
